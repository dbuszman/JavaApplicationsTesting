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
When removing tree where name is Jablon, type is Lisciaste and amount is 2
Then trees should decrease by 1