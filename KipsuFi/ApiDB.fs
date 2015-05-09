namespace KipsuFi

open WebSharper.Html.Server
open WebSharper
open WebSharper.Sitelets

module Api =
    open System.Web

    let Algorithms() =
        Content.JsonContent <| fun context ->
            DB.algorithms()

    let Datastructures() =
        Content.JsonContent <| fun context ->
            DB.datastructures()
           