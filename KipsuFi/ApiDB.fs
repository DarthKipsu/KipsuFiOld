namespace KipsuFi

open WebSharper.Html.Server
open WebSharper
open WebSharper.Sitelets

module Api =
    open System.Web

    type Data =
        {
            Group : string
            Content : string list
        }

    let WithTemplate title body : Content<Action> =
        Content.JsonContent <| fun context ->
            {
                Group = title
                Content = body context
            }


    let AlgorithmPage() =
        Content.JsonContent <| fun context ->
            DB.algorithm_names()

    let DatastructuresPage =
        WithTemplate "Datastructures" <| fun ctx ->
            DB.datastructure_names()
           