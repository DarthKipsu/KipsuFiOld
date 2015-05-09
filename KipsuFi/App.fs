namespace KipsuFi

open WebSharper.Html.Server
open WebSharper
open WebSharper.Sitelets
open System.Web

module Site =

    let Main =
        Sitelet.Infer <| function
            | Main -> Skin.MainPage
            | ListAlgorithms -> (Api.Algorithms())
            | Algorithms id -> (Api.Algorithm <| HttpUtility.UrlDecode(id))
            | ListDatastructures -> (Api.Datastructures())
            | Datastructures id -> (Api.Datastructure <| HttpUtility.UrlDecode(id))

[<Sealed>]
type Website() =
    interface IWebsite<Action> with
        member this.Sitelet = Site.Main
        member this.Actions = []

type Global() =
    inherit System.Web.HttpApplication()

    member g.Application_Start(sender: obj, args: System.EventArgs) =
        ()

[<assembly: Website(typeof<Website>)>]
do ()
