namespace KipsuFi

open System.Data
open Npgsql

module DB =
    let connection() =
        let conn = new NpgsqlConnection("Server=127.0.0.1;Port=5432;User Id=kipsu;Password=Salainen1;Database=codingproject;")
        conn.Open()
        conn

    let readNamesToList query =
        let conn = connection()
        let command = new NpgsqlCommand(query, conn)
        try
            let reader = command.ExecuteReader()
            [while reader.Read() do yield reader.[0] :?> string]
        finally
            conn.Close()

    let algorithm_names() =
        readNamesToList "SELECT algorithm_name FROM algorithms"

    let datastructure_names() =
        readNamesToList "SELECT datastructure_name FROM datastructures"