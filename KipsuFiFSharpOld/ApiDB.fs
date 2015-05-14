namespace KipsuFi

open WebSharper.Html.Server
open WebSharper
open WebSharper.Sitelets

module Api =
    open System.Web

    let Algorithms() =
        Content.JsonContent <| fun context ->
            DB.algorithms()

    let Algorithm id =
        Content.JsonContent <| fun context ->
            DB.algorithm id

    let Datastructures() =
        Content.JsonContent <| fun context ->
            DB.datastructures()

    let Datastructure id =
        Content.JsonContent <| fun context ->
            DB.datastructure id
           