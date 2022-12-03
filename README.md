# Vision

* An Open Source drill writing application, in which you can download the core for free

* Anyone can use the core of the application for non-commercial purposes free of charge

* If someone wants to use any part of the system for commercial purposes, they have to pay some sort of fee

* Developers can add individual features as plugins (similar to Eclipse), which can be for charge or for free

* Financial gain can be done through support, plugins, and books

# Why?

Take into account that VDrill this was written in 2000 by music major at TCU. At the time there was no unit testing, continuous integration, or any type of static analysis. There was also a missing of the basics of computer science, but I managed and used it as a learning project. Overtime I tried to improve things, but it would always be limited by design decisions I would be unable to change without better automated testing in place.

The only way to add a lot of the requested functionality in a way that would be more maintainable, I have determined would require starting this again from the ground up. I could also take advantage of all that fancy computer science undergraduate and graduate education, and the 15 years of software industry experience. This is why I am making another attempt at this.

# Phase I: How do I go about doing this?

I started with the lowest level element possible, the marcher, and attempted to bring it in as a marcher object and a marcher service used for performing marcher level operations. The problem though is that too much of the old application relies on functionality within objects, so when I tried to bring over the Segment I found cases of segment services chaining with marcher level services. In this end this won't work because those operations are tied to the functions that I want to be external plugin functionality.

For this reason I am going to attempt to move on to the basics of the UI, and start working on the plugin functionality piece by piece. For example when I want to add the float I go into the old code and pull all the code that has to do with floating and put it into a plugin. This will result in pulling over the code in the manner that I want it, instead of having to do the after-the-fact refactor.

The advantage the code has so far over the old version though is the separation between domain object and the operation on that object, and that the operation is actually tested now. The old code was done before unit testing existing, and only a very small percentage of the code had any sort of testing. This lack of testing is what made the old code so difficult to change. We got to a point where big changes were not feasible to manually test.
