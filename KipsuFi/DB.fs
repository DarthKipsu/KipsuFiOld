namespace KipsuFi

open System.Data
open Npgsql
open FSharp.Data

module DB =
    type Feature =
        {
            Name: string
            Advantage: bool
        }

    type Algorithm = 
        {
            Name: string
            Description: string
            Datastructures: string list
            Features: Feature list
        }

    type StringParser = JsonProvider<""" ["item"] """>
    type BooleanParser = JsonProvider<""" [true] """>

    let connection() =
        let conn = new NpgsqlConnection("Server=127.0.0.1;Port=5432;User Id=kipsu;Password=Salainen1;Database=codingproject;")
        conn.Open()
        conn

    let readNamesToList query func =
        let conn = connection()
        let command = new NpgsqlCommand(query, conn)
        try
            try
                let reader = command.ExecuteReader()
                [while reader.Read() do yield func reader]
            with
                | :? System.Exception as e -> raise e
        finally
            conn.Close()

    let algorithm_names() =
        readNamesToList
            """SELECT a.algorithm_name, a.description, json_agg(DISTINCT ad.datastructure_name), json_agg(af.feature_name), json_agg(af.advantage) FROM algorithms a natural join algorithms_datastructures ad, algorithms_features af GROUP BY a.algorithm_name, a.description ORDER BY 1"""
            (fun reader -> 
                let name = reader.[0] :?> string
                let description = reader.[1] :?> string
                let datastructures = StringParser.Parse(reader.[2] :?> string)
                let featureNames = StringParser.Parse(reader.[3] :?> string)
                let featureAdvantages = BooleanParser.Parse(reader.[4] :?> string)
                let features = [for i=0 to (featureNames |> Array.length) - 1 do yield {
                                    Name = featureNames.[i]
                                    Advantage = featureAdvantages.[i]
                                }]
                {
                    Name = name
                    Description = description
                    Datastructures = datastructures |> List.ofArray
                    Features = features
                 })

    let datastructure_names() =
        readNamesToList
            "SELECT datastructure_name FROM datastructures"
            (fun reader ->  reader.[0] :?> string)