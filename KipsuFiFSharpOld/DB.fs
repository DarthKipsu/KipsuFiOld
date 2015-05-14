namespace KipsuFi

open System.Data
open Npgsql
open FSharp.Data
open System.Data.SqlClient

module DB =
    type Algorithm = 
        {
            Name: string
            Description: string
            Datastructures: string list
            Advantages: string list
            Disadvantages: string list
        }

    type Datastructure = 
        {
            Name: string
            Description: string
            Advantages: string list
            Disadvantages: string list
        }

    type AlgorithmPage =
        {
            Name: string
            Description: string
            Datastructures: string list
            Advantages: string list
            Disadvantages: string list
            Content: string
        }

    type DatastructurePage =
        {
            Name: string
            Description: string
            Advantages: string list
            Disadvantages: string list
            Content: string
        }

    type StringParser = JsonProvider<""" ["item"] """>
    type FeatureParser = JsonProvider<""" [{"f1": "name", "f2": true}] """>

    let connection() =
        let conn = new NpgsqlConnection("Server=127.0.0.1;Port=5432;User Id=kipsu;Password=Salainen1;Database=codingproject;")
        conn.Open()
        conn

    let readRecord query func id =
        let conn = connection()
        let command = new NpgsqlCommand(query, conn)
        if (id |> String.length) > 0 then 
            command.Parameters.Add(NpgsqlParameter("@id", id)) |> ignore
        try
            let reader = command.ExecuteReader()
            [while reader.Read() do yield func reader]
        finally
            conn.Close()

    let parseFeature features selector comparator =
        [for feature in features do if comparator feature then yield selector feature]

    let algorithms() =
        readRecord
            """SELECT a.algorithm_name, a.description, json_agg(DISTINCT ad.datastructure_name), json_agg((af.feature_name, af.advantage)) FROM algorithms a natural join algorithms_datastructures ad, algorithms_features af GROUP BY a.algorithm_name, a.description ORDER BY 1"""
            (fun reader -> 
                let features = parseFeature (FeatureParser.Parse(reader.[3] :?> string)) (fun f -> f.F1)
                {
                    Algorithm.Name = reader.[0] :?> string
                    Description = reader.[1] :?> string
                    Datastructures = StringParser.Parse(reader.[2] :?> string) |> List.ofArray
                    Advantages = features (fun f -> f.F2)
                    Disadvantages = features (fun f -> not f.F2)
                 }) ""

    let algorithm name =
        readRecord
            """SELECT a.algorithm_name, a.description, a.content, json_agg(DISTINCT ad.datastructure_name), json_agg((af.feature_name, af.advantage)) FROM algorithms a natural join algorithms_datastructures ad, algorithms_features af WHERE a.algorithm_name = @id GROUP BY a.algorithm_name, a.description ORDER BY 1"""
            (fun reader -> 
                let features = parseFeature (FeatureParser.Parse(reader.[4] :?> string)) (fun f -> f.F1)
                {
                    AlgorithmPage.Name = reader.[0] :?> string
                    Description = reader.[1] :?> string
                    Datastructures = StringParser.Parse(reader.[3] :?> string) |> List.ofArray
                    Advantages = features (fun f -> f.F2)
                    Disadvantages = features (fun f -> not f.F2)
                    Content = reader.[2] :?> string
                 })
                 name |> List.head

    let datastructures() =
        readRecord
            "SELECT d.datastructure_name, d.description, json_agg((df.feature_name, df.advantage)) FROM datastructures d natural join datastructures_features df GROUP BY d.datastructure_name, d.description ORDER BY 1"
            (fun reader -> 
                let features = parseFeature (FeatureParser.Parse(reader.[2] :?> string)) (fun f -> f.F1)
                {
                    Datastructure.Name = reader.[0] :?> string
                    Description = reader.[1] :?> string
                    Advantages = features (fun f -> f.F2)
                    Disadvantages = features (fun f -> not f.F2)
                 }) ""

    let datastructure name =
        readRecord
            "SELECT d.datastructure_name, d.description, d.content, json_agg((df.feature_name, df.advantage)) FROM datastructures d natural join datastructures_features df WHERE d.datastructure_name = @id GROUP BY d.datastructure_name, d.description ORDER BY 1"
            (fun reader -> 
                let features = parseFeature (FeatureParser.Parse(reader.[3] :?> string)) (fun f -> f.F1)
                {
                    DatastructurePage.Name = reader.[0] :?> string
                    Description = reader.[1] :?> string
                    Advantages = features (fun f -> f.F2)
                    Disadvantages = features (fun f -> not f.F2)
                    Content = reader.[2] :?> string
                 })
                 name |> List.head