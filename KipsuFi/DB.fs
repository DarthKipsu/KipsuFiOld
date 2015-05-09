namespace KipsuFi

open System.Data
open Npgsql
open FSharp.Data

module DB =
    type Algorithm = 
        {
            Name: string
            Description: string
            Datastructures: string list
            Advantages: string list
            Disadvantages: string list
        }

    type StringParser = JsonProvider<""" ["item"] """>
    type FeatureParser = JsonProvider<""" [{"f1": "name", "f2": true}] """>

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

    let algorithms() =
        readNamesToList
            """SELECT a.algorithm_name, a.description, json_agg(DISTINCT ad.datastructure_name), json_agg((af.feature_name, af.advantage)) FROM algorithms a natural join algorithms_datastructures ad, algorithms_features af GROUP BY a.algorithm_name, a.description ORDER BY 1"""
            (fun reader -> 
                let features = FeatureParser.Parse(reader.[3] :?> string)
                {
                    Name = reader.[0] :?> string
                    Description = reader.[1] :?> string
                    Datastructures =
                        StringParser.Parse(reader.[2] :?> string) |> List.ofArray
                    Advantages = 
                        [for feature in features do if feature.F2 then yield feature.F1]
                    Disadvantages = 
                        [for feature in features do if not feature.F2 then yield feature.F1]
                 })

    let datastructure_names() =
        readNamesToList
            "SELECT datastructure_name FROM datastructures"
            (fun reader ->  reader.[0] :?> string)