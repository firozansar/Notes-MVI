# Notes MVI

Notes-MVVM implements Unidirectional Data Flow (UDF) concepts introduced by [Redux](https://redux.js.org/). The core idea is that user Actions get dispatched to a Store (State container) which uses Reducers to transform them into States. In Android world, this design pattern is known as MVI (Model-View-Intent) where Model describes State and Intent describes user interaction.

The Unidirectional Data Flow (UDF) can be summarized as follows:
1. User interaction events (Actions) get dispatched to ViewModel via a single pipeline.
2. Each Action gets transformed into Changes.
3. Each Change combined with a previous State produces a new State using a Reducer.
4. UI observes new States via a single pipeline and renders them as they come in.

Some of the benefits of UDF are:
* State Machine managing immutable States makes data predictable and easier to manage.
* Support for state restoration after device rotation and process death.
* A set of Actions, Changes, and States for each screen result in a thorough user-centric design.
* Logging of Actions and States makes both debugging and crash reporting extremely efficient.
* Rich RxJava APIs help achieve composable functional code.
* Meaningful and consistent unit tests asserting that given Actions and initial State produce correct new States.

## Documentation 

Some of the topics covered are:

* [ViewModel](https://github.com/ww-tech/roxie/wiki/1.-ViewModel)
* [Actions](https://github.com/ww-tech/roxie/wiki/2.-Actions)
* [Changes](https://github.com/ww-tech/roxie/wiki/3.-Changes)
* [States](https://github.com/ww-tech/roxie/wiki/4.-States)
* [Reducer](https://github.com/ww-tech/roxie/wiki/5.-Reducer)
* [Rendering State](https://github.com/ww-tech/roxie/wiki/6.-Rendering-State)
* [Logging](https://github.com/ww-tech/roxie/wiki/7.-Logging)
* [Process Death](https://github.com/ww-tech/roxie/wiki/8.-Process-Death)
* [Unit Tests](https://github.com/ww-tech/roxie/wiki/9.-Unit-tests)

