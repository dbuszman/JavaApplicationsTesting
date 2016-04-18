Scenario: Counting test
Given a TreeManager
When set name to: Jablon, type to: Lisciaste and amount to: 2
Then adding should increase trees by 1


Scenario: Adding test
Given a TreeManager
When set name to: Jablon, type to: Lisciaste and amount to: 2
Then added tree should have name Jablon

Scenario: Removing test
Given a TreeManager
When set name to: Jablon, type to: Lisciaste and amount to: 2
Then removing should decrease trees by 1