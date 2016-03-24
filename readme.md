# Vaadin tips and tricks

This project and document contains a collection of tips and tricks that will hopefully make it easier and more fun for you to work with Vaadin. 

Note, this is not a challenge. Please do not try to incorporate everything you see here into your project all at once unless it makes sense for your project. As always, use common sense and strive for the simplest solution that solves your problem.


## Use dependency management

Unless you find some sort of twisted pleasure in scouring Google for jar files, I suggest that you use a tool like Maven to handle your dependencies. It may be fairly straight forward to get your project up and running without, but maintenance will become a big headache in the long run. 
 
If you are using Maven, you can additionally benefit from the archetypes we provide to kickstart your project: 
 
* vaadin-archetype-application - basic application skeleton
* vaadin-archetype-application-multimodule - multimodule project with production and development profiles
* vaadin-archetype-application-example - an example CRUD app with a responsive layout
* vaadin-archetype-widget - startingpoint for building your own widgets

All the archetypes are listed [here](https://vaadin.com/maven#archetypes).

## Use the Navigator

Vaadin comes with a built in `Navigator` helper that maps between Views and uri fragments. This allows you to create bookmarkable views within your application. Correctly used, the `Navigator` will also help you keep your application memory usage in check. 

### Login and initializing the Navigator

Don't initialize the Navigator unless a user is logged in. Keep your login view separate from the rest of your navigation, and only create the Navigator once the user has authenticated. This way, you do not inadvertently expose any content to unauthorized users, and you may select which views to register for a certain user (for instance only register an admin view if an admin logged in). See `DemoUI.init()` and `AwesomeApp#setupNavigator()`.

Once the user is logged in, you want to call `navigator.navigateTo(navigator.getState())` to make sure the user is forwarded to the page they were trying to access before getting redirected to the login page. 

### Use constants for View names

Define your view names in constants to avoid making typos. In this project, I created a custom annotation `ViewConfig` that I used to store the view name, display name (for the menu and page title) and the creation modes (I'll cover creation modes shortly). The main thing to keep in mind is that **you do not want to be typing view names by hand in every place you use them.**
 
See `OrdersView` for an example use of how I've used the `ViewConfig` annotation. The metadata is then used in `AwesomeApp.addView` to both register the view to the `Navigator` and to add it to the navigation bar.

### Keep memory use and load times in check with correct View registration

When registering a view, be very careful how you do it. Doing it wrong may cause your application to use significantly more memory than needed. By default, there are two ways of doing this:

1. `navigator.addView("viewname", View.class)`
2. `navigator.addView("viewname", new View())`

The difference between these, is that #1 will always give you a new instance of a View when you navigate to it, whereas #2 always returns the same instance. **Unless you have very compelling reasons to use #2, you should always use #1**. If you use #2, the Navigator needs to keep a reference to the View in order to return it later. This means that no Views can be garbage collected and so registering views this way will significantly increase the memory used per session.  

If you have a case where you need the same instance to always be returned, you should defer the instantiation of that View until it is actually needed. This way you don't increase the initial startup time of your application. `LazyProvider` contains an example implementation of how to do this. 

In this demo project, we define the instantiation mode through the `@ViewConfig` annotation. You can see the actual view registration in `AwesomeApp.addView`.

### Use ViewChangeListeners wisely

`ViewChangeListener` is a very handy way of attaching logic to view transitions. In this project, we use them to update the selected menu item in the navigation bar and for setting the Page title. See `AwesomeApp.setupNavigator` for the registration of the listeners and `NavBar#afterViewChange` and `PageTitleUpdater` for implementations. 

You can also implement access control by doing checks in a `ViewChangeListener` and returning `false` from the `beforeViewChange` method. Please remember though that the best way of preventing access to views is to not register views in the first place for a user that doesn't have access. 

### Always define an error view

**Always!** This is the equivalent to a 404 page for your Vaadin application. Without this, any user navigating to an incorrect URL will get an ugly stacktrace. 

### Use the enter method for initializing a View

Especially if you are eagerly instantiating your Views, you should defer any actual data lookup and initialization to the enter method that is shown when the user actually opens the view. 


## Components and layouting

Vaadin is a component based framework. Because everything in Vaadin is a component and defined in Java, you can easily create your own reusable compositions. 

For instance -- in this project, instead of having to set up a Grid from scratch in each of the Order examples, I opted to extend Grid and create a more specialized OrderGrid, that already contained the needed column setup and converters I needed. This way, I could keep the View implementations simple and to the point. 

### CustomComponent or direct inheritance? 

When creating a composition, you have two options: using `CustomComponent` or just extending an existing component. The benefit of using `CustomComponent` is that it hides all internal implementation details and allows you to specify the Component API. The disadvantage of using `CustomComponent` is that it will increase complexity, both in your code and in the DOM, which can in some cases have an impact on rendering speed. 

In general, I prefer directly extending a component to keep things simple and lightweight. If you are creating a component for someone else to use, you may consider using a CustomComponent to wrap it. 

### Spacing and margins

One often overlooked feature of the Vaadin layouts is `setMargin` and `setSpacing`. In most cases, turning these on will give you a much more visually pleasing result, as all your components aren't squished together. In fact, I use this so often that I created a custom `VerticalSpacedLayout` that defaults to having them enabled. 

### Sizing and positioning
Vaadin has a very powerful layout engine. If you understand how it works, you will have a very powerful tool for creating fluid layouts, but used wrong it will cause a lot of headaches. 

There are two different sizes you need to take into account when working with the default Vaadin layouts (Vertical, Horizontal, Grid): the size of a component and the size of the layout slot it is in. A component's size, what you get when you call `setSize()` is the size of the component in relation to the layout slot the component is in. The size of the layout slot is determined by calling `setExpandRatio()` on the containing layout. ExpandRatio will determine how any unused space should be divided between the components. If you do not specify any values, all slots will be equally sized. The position of a component is also relative to the slot it is in.

Another common mistake I see in Vaadin projects is having relatively sized components inside layouts without a defined size. So if you for have a VerticalLayout that by default has an undefined height (it will grow as needed with the content), you cannot put a component with a 100% height inside of it. Vaadin will not be able to calculate 100% of null, so the component will not be shown. You can easily locate these types of issues by running your application with the debug window open (append `?debug` to your application URL) and runnin the analyze layouts tool.

## Theme

Vaadin comes with a powerful Sass based theme builder called Valo. Valo allows you to customize the look and feel of your application by configuring a set of high-level parameters. You can make large modifications to your application with just a single change, as unspecified values will be automatically populated with reasonable values. For instance, if you change the background color to a dark color, Valo will change text and highlight colors to a lighter color to ensure sufficient contrast. 

When working with themes, it is usually a good idea to store CSS class names in a helper class as constants. In this application, we extend the Valo theme, so our theme helper class `MyTheme` extends the `ValoTheme` helper, to give us access to all the already defined classnames. See `LoginBox` for an example of using the helper class.
 
When building your application, you usually want to add reasonable CSS classnames to relevant layouts and components. This will make changing the look and feel much easier later on. 

When styling your application, try to stay away from writing selectors against any `.v-` prefixed classnames, as this will make your CSS more bound to the specific implementing class. So, instead of styling `.v-vertical-layout`, give your layout a meaningful name with `addStylename()` and write your selector for that. This way, you have more freedom in changing the implementing layout without affecting your styles. 

Take advantage of the fact that Sass allows you to split up your stylesheets into separate files without incurring any runtime overhead. You will usually want to keep styles for different views/components in separate files for easier maintenance. When compiled, all your CSS will be in one file. 

**Remember that you need to manually compile the SCSS before moving to production, as the automatic compilation is disabled in production mode!**

## Dealing with large data sets

A common question in Vaadin development is how one should handle large data sets. In this application, I've created three examples to show the typical approaches to solving the problem. We're working with a order system that has around 10,000 orders and is quite slow. Let's look at the pros and cons of each approach below. 

### First option: Load all data and put it in a container

The most straight-forward solution is of course that we just load all the Orders, put them in a Container, and tell our Grid to display the data. This example can be found in `OrdersView`.
 
The problem with loading all data upfront is that the application will feel very sluggish to the user. We will also end up using a significant amount of  memory on the server. For systems with a few concurrent users, this may not be an issue worth addressing. But for systems with a lot of concurrent users it will make a difference.  

The benefit of this approach is of course it's simplicity. If you are OK with the page taking a while to load and only have a few users on a server concurrently, this simple approach is just fine. 

### Second option: Load data asynchronously 

In order to make the application more responsive, we'll improve our solution by delegating the long running application to a background thread and show the user a message and a spinner while we fetch the data. 

By immediately loading the page and informing the user what we're doing, the system will feel much more responsive. Users tend to tolerate much longer delays if you inform them what is happening instead of just having the UI hang. Once the data are available, we'll again populate a container and swap out a Grid instead of our placeholder spinner. Source code for this step can be found in `AsyncOrdersView`. 

**Note:** When updating the UI from a background thread, we need to use either polling or push to allow the server to initiate a UI update. Remember to use 
the `getUI().access()` helper to properly handle locking while updating the UI. Also note that helpers such as `UI.getCurrent()` which depend on `ThreadLocal`s will not work in background threads, so be sure that you pass your background thread all the needed info.
 
This version is already much better from a usability standpoint, but will still suffer from the same memory usage problem as our first attempt. 

### Third option: Lazily loading data as it's needed

In order to address both initial load speed and memory usage, our third option (found in `LazyOrdersView`) will only query as much data as it can show in the grid when you open the page, and will fetch more data as needed when you scroll down. 

The third option is clearly better than the two previous from a memory consumption perspective. The downside is that the implementation is much more complex to implement, especially if you need to support sorting and filtering. In this example, I based the solution on `LazyList` from [Viritin](https://vaadin.com/directory#!addon/viritin). There are other lazy loading container implementations such as the JPAContainer, SQLContainer, and [LazyQueryContainer](https://vaadin.com/directory#!addon/lazy-query-container) that can help you with the implementation. 

### But do you really need all that data?  

Before spending too much time on figuring out how to display 100,000 rows of data in a table, I sincerely suggest that you take a step back and think about what you are doing. Showing thousands of rows of data to a user is rarely the ideal way of presenting data. How often do you look up the business hours of a restaurant by searching 'restaurant' in Google and clicking yourself all the way to page 12,043?

If possible, try to offer your user better search tools or filters so you don't need to deal with huge data sets in the first place. If you only need to show 50 rows instead of 100,000, putting them in an in-memory container won't be an issue anymore.  

## Forms

Another very common question from Vaadin developers is how to create forms easily. I've tried to show you some tips in the `FormView` class. 

Usually, it is easiest to keep your form in a Component of its own. This way, you can easily tell `FieldGroup` to scan the component for your fields to bind. Remember that the names of your fields need to exactly match the property ids in your `Item`, otherwise you'll need to annotate them with `@PropertyId`, like I did in `FormLayout`. 

### Use custom fields to handle lists and nested objects

Unless your form only contains strings and numbers, there's a good chance you'll want to create your own Fields to handle the editing of those. In our example, I've created a custom field for editing the list of line items in an Order. You can see the implementation in `LineItemField`. The custom field is essentially just an embedded `FieldGroup` for editing line items in our order.

By creating our own fields, we can keep the main form simple, and we're able to reuse the implementation in other parts of the software. 

### Validation and improving the user experience

Unfortunately, validating forms in Vaadin is not very nice. If you attach a validator to a field, it will already tell the user there's an error before the user has even started filling in the form. 

Improving the validation is a bit of work, so I created a helper found in `FieldGroupUtil.improveUX`. Calling this method will turn on live validation and make sure that we don't bug our users with validation errors before they've even tried to populate a field. 

In addition, the utility will help improve UX by toggling the enabled state of your save/cancel buttons. The cancel button won't be active until a user has made a change to the form, and the save button will only be active once all validations pass.

Please note that this is a slightly simplified solution to keep the code understandable, please refer to AbstractForm, MBeanFieldGroup, and MTextField in [Viritin](https://github.com/viritin/viritin) for a more complete solution.

## Design patterns

Rather than getting myself into an ideological warfare over the One Right Way&trade; of building software, I'll share a couple of practices that can hopefully help you improve your code quality and productivity. 

The two things I'll focus on are decoupling and separation concerns. 

### Decoupling your code

As you have seen, Vaadin allows you to easily create custom components and compositions so you can work with components that are tailored to your needs. All the reusability benefits of building these compositions fly out the window immediately if your `UsefulForm` depends directly on `VerySpecificView`. One solution for this is to use an observer pattern and using callback interfaces. This is how Vaadin components work. In order to have a `Button` do your work, you give it a `ClickListener` and it'll let you know when it was triggered. This is a pattern that you can also use in your own components. 
 
When communicating between different parts of your application, a generic EventBus can be incredibly valuable. An event bus allows you to publish any type of event to a central dispatcher that will then call all registered listeners. So in essence, the same idea as with the `ClickListener`, just with a broker in between. 

In this sample application you can for instance see that I'm firing `NavigationEvent`s from the NavBar. `DemoUI` is subscribed to these events and will make sure we get navigated where we need to go. `NavBar` is fully unaware of the fact that it is the `DemoUI` that implements the functionality. To keep the tech stack as simple as possible in this project, I've used the [Google Guava](https://code.google.com/p/guava-libraries/) event bus. CDI events in Java EE are also a great way of accomplishing the same. 

Please don't go overboard with the use of an event bus. The downside of this level of decoupling is that understanding program flow can become very difficult. So again, use your judgement and use the tools you have responsibly.

### Separation of concerns

Another important topic is separation of concerns. In practice, this tends to manifest itself as a Model View Whatever (MVP, MVC, MVVM) pattern where you separate the UI logic from the layouts and components. This enables us to better test our UI code and makes it easier to find issues in our code as the logic is not split into tens of different UI components. 

You can see one very simple MVP example in `FormView`/`FormPresenter` (`Order` is the M in this case). In a real world application you will most likely end up creating abstract View and Presenter classes to handle common use cases like registering views and showing success/error messages.

The actual implementation is in my opinion less important than the fact that you keep the concerns separated.

Be sure to read [this excellent article on using MVP and Vaadin](https://vaadin.com/blog/-/blogs/is-mvp-a-best-practice-) written by our main architect at Vaadin. 

## Testing

There are a few levels of testing that we can do with our Vaadin application. Here, we'll look at unit/integration testing and regression testing. 

## Unit testing our UI logic

Since we split our `FormView` into a View/Presenter pair, we can easily write normal JUnit tests for the Presenter without having to deal with instantiating Vaadin components or figuring out things like how to stub out static calls to VaadinSession. 
 
`FormPresenterTest` shows a simple test that uses mock objects instead of both `OrderService` and `FormView`. This way we have full control over the tests and can exercise things like exception handling without having to manipulate an external system. 

Note that by changing at what level you stub out the rest of the system, you can also write integration tests in the same way. We could for instance have used an actual OrderService implementation instead of the mock to exercise the entire system.

## Full stack regression testing

On top of the more low-level unit tests, it's good to write some broader regression tests to ensure that you don't break your app by mistake while working on something else. [Vaadin TestBench](https://vaadin.com/add-ons/testbench) is a tool that is built for creating regression tests for Vaadin apps. It will allow us to build tests that run our application through an actual browser. 

`FormRegressionIT` shows an example of a test that fills in our order form and submits it. TestBench also allows us to measure performance. In `RenderingSpeedIT` we have built a test that ensures a view is rendered quicker than a given threshold. 

**Note** Vaadin TestBench is a commercial add-on for Vaadin. You can obtain free licenses for non-profit use on our web page. There is also a 30 day trial available. In order to run the tests in this project you'll need to have [Firefox ESR](https://www.mozilla.org/en-US/firefox/organizations/all/) installed.

Note that integration tests are not typically executed all the time during development or basic development build - as the require a considerably more time than simple unit tests and they require you to have a server up and running. You can run integration tests as JUnit tests form your IDE (when you have the project running in a server). Alternatively you can execute them with Maven using "it" profile. That profile also starts and automatically deploys the application using a Jetty server. Typically you'd have this profile enabled in your CI server. The maven command that runs integration tests:

mvn verify -Pit

## Optimization

We've already seen that the speed of our application can play a big role in how our users perceive our app. In the previous example, we were able to remedy the issue by not loading the entire large dataset upfront. What if the cause for slowness isn't as apparent?

Before we can start fixing the problem, we need to figure out what the problem is. One good starting point is to see whether the slowness is on the server side or in the browser. You can use the Vaadin debug window to do this. Launch your application with the `?debug` parameter and look at the console output. We are interested in two things:
 
1. Server visit took **XXms**
2. Processing time was **XXms** for NNN characters of JSON

The first tells us how long we spent on the server processing the request, the second tells us how long the browser spent rendering the result. If the time taken on the server is significantly lower than the time for rendering, we'll have to start looking at what might cause rendering slowness.

## Optimizing slow rendering

Slow rendering is almost always caused by unnecessarily complex and deep component hierarchies. If you have complex layouts, see if you could get rid of some wrapping layouts, Panels or CustomComponents. 

Try changing the implementation used in `SlowRenderingView` to `QuickOrderLayout` and you'll see that the render time drops to about a third of what it was initially.

Again, please start with a simple solution and only optimize if you actually identify a problem. Flattening our hierarchy by inlining HTML is similar to optimizing a function in assembler in a C. You will trade code clarity and strong typing for optimization. 

## Profiling the server

If the slowness is on the server, you'll want to use a profiler to see where in the code you're spending most time. Typically it will be in fetching data. You may want to consider asynchronous loading if the delay is very noticeable and you cannot optimize it by tweaking DB indexes or optimizing your service level code.

You will also want to check on your application memory usage to keep things running smoothly in production. Download [VisualVM](https://visualvm.java.net/) and attach to your running server. See the difference in how memory is freed when changing how you instantiate your Views. Try changing the create mode of `HeapDestroyer` from LAZY to ALWAYS_NEW and you'll notice that the second option allows us to immediately free the memory once the user is no longer on the view. 

## Keep things simple, refactor constantly

Always try to keep your project as simple as possible. Don't introduce any new technology/pattern/whatever unless there is really a need for it. 

Also remember to refactor constantly. Instead of trying to figure out and plan for all possible reusable components up front, start by building concrete implementations. When you feel like you are building the same thing for the second time, it's time to refactor the commonalities into a common class. I feel that working this way will keep you moving quickly and help avoid unnecessary complexity. 

## Ask for help before you're in trouble

Often having a quick 15 minute talk with a Vaadin expert before you start programming can save you a lot of debugging and refactoring later. We're here to help you with anything form short problem solving tasks to long term consulting, [just give us a call or email us](https://vaadin.com/company).