qlearner
========

[![Build Status](https://travis-ci.org/Cantido/qlearner.svg?branch=master)](https://travis-ci.org/Cantido/qlearner)

A demonstration of the q-learning reinforcement learning technique. For more
information about the technique itself, see [its Wikipedia page][wiki].

This program uses the q-learning technique to move an agent through a grid.
There is a starting square and a goal square, and the agent has to
learn, using q-learning, how to reach the goal in the fewest number of moves.

Usage
-----

The package `qlearning.example.gridworld` and its tests demonstrate how to build a qlearner to move throughout
a grid, from a starting state, to a goal state. It all starts with an `Agent` class.

#### Agent

The **Agent** class the entry point to this software, and has only one method: `takeNextAction()`.

```
Agent agent = new Agent(environment, explorationStrategy, learningRate, discountFactor, qualityMap);

while(true) {
    agent.takeNextAction();
}
```

Each call to `Agent.takeNextAction()` will:

1. Get the current `State` object from the given `Environment`
2. Update the learned values based on this new state's `Reward` 
4. Choose and execute the next `Action` given by the current `State`

To implement your own, you must implement the following interfaces:

#### Environment

Implementations of the **Environment** interface represent some system (real-world or otherwise) that you are
trying to affect. This program will use a single instance of an `Environment` class to fetch data on every
learning iteration.

#### State

Implementations of the **State** interface are returned by `Environment` classes via
`Environment.getState()`, and represent a certain condition of the system you are trying to affect. If an
`Environment` class is a temperature sensor, a `State` class would be an individual temperature reading.

A `State` class has a `Reward` value, that represents how desirable it is. This program tries to maximize positive
rewards and minimize negative rewards.

**Warning**: This program does not yet have the functionality necessary to learn continuous values. You must build your
`State` objects to round these continuous values to the point where it is likely that the program will see that
value again. For instance, round temperature value to a smaller precision. See the
`qlearning.impl.QualityHashMap` class for more information.

`State` classes area also responsible for returning the `Action` classes that it is possible to take from that
state.

#### Action

Implementations of the **Action** interface represent a thing that can be done to affect the `Environment`. This
program tries to learn the best `Action` to take for a given `State`. Each iteration of this program calls
`Action.execute()` once.

Domain Values
-------------

#### Reward

Specifying **Reward** values is the core way that you tell this program what you want. Higher reward values
mean more desirable states. This program tries to maximize positive rewards and minimize negative rewards.

Do not try to over-think things when specifying the reward value of a certain state. The domain objects of
this program were organized with a `State` being responsible for a `Reward` because
**you should not be rewarding behavior, you should be rewarding state.** This program *learns behavior*, and
can probably do a better job at it than a human can. No offense.

Certain values can be specified to affect how this program learns:

1. The **LearningRate** affects how quickly the algorithm will adapt to changing conditions. This value needs to
be chosen in correlation with how often a given `State`-`Action` pair will lead to the same next `State`. In
fully deterministic environments, a value of 1 is optimal.
2. The **DiscountFactor** determines the importance of future rewards. Higher discount factors will make the program
favor states with higher long-term rewards. Lower discount factors will make the program favor states that have higher
immediate rewards.
3. The **ExplorationFactor** is used to determine how likely the program will pick non-optimal actions in order to
better explore the problem space. By default, this program uses a random exploration strategy, and higher values of
this variable will make the program more likely to pick a random `Action`.

License
-------
Released under the [GNU General Public License, Version 3](http://www.gnu.org/licenses/gpl.html)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

[wiki]: https://en.wikipedia.org/wiki/Q_learning "Q-learning - Wikipedia, the free encycopedia"
