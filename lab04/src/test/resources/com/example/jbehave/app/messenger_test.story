Scenario: Connection failure
Given a messenger
When set server as edu
Then test connection should return 1

Scenario: Connection success
Given a messenger
When set server as inf.ug.edu.pl
Then test connection should return 0

Scenario: Server address or message is not proper
Given a messenger
When set server to: <server> and message to: <message>
Then sending message should be 2

Examples:
|server|message|
|inf.ug.edu.pl|ab|
|edu|some message|
|edu|ab|
|inf.ug.edu.pl|not set|
|not set|not set|
|not set|ab|
|not set|correct message|

Scenario: Server address and message is proper
Given a messenger
When set server to: inf.ug.edu.pl and message to: some message
Then sending message could be 0 or 1

Given a messenger
When set server to: trojmiasto.gda.pl and message to: correct message
Then sending message could be 0 or 1
