namespace KipsuFi

open WebSharper.Html.Server
open WebSharper
open WebSharper.Sitelets

module Site =

    let Main =
        Sitelet.Sum [
            Sitelet.Content "/main" Main Skin.MainPage
            Sitelet.Content "/algorithms" Algorithms (Api.Algorithms())
            Sitelet.Content "/datastructures" Datastructures (Api.Datastructures())
        ]


[<Sealed>]
type Website() =
    interface IWebsite<Action> with
        member this.Sitelet = Site.Main
        member this.Actions = [Main; Algorithms; Datastructures]

type Global() =
    inherit System.Web.HttpApplication()

    member g.Application_Start(sender: obj, args: System.EventArgs) =
        ()

[<assembly: Website(typeof<Website>)>]
do ()
