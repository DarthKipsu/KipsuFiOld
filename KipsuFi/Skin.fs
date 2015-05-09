namespace KipsuFi

open WebSharper.Html.Server
open WebSharper
open WebSharper.Sitelets

module Skin =
 
    type Page =
        {
            Title : string
            Body : list<Content.HtmlElement>
        }
 
    let MainTemplate =
        Content.Template<Page>("~/Main.html")
            .With("title", fun x -> x.Title)
            .With("body", fun x -> x.Body)

    let WithTemplate title body : Content<Action> =
        Content.WithTemplate MainTemplate <| fun context ->
            {
                Title = title
                Body = body context
            }

    let MainPage =
        WithTemplate "no joku title" <| fun ctx ->
            [
                Div [Text "kipsun sivut"]
                Div [A [HRef "/algorithms"] -< [Text "Algorithms"]]
                Div [A [HRef "/datastructures"] -< [Text "Datastructures"]]
            ]