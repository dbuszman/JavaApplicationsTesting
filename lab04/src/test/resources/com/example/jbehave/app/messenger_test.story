Given a messenger
When set server to inf.ug.edu.pl and message to ab
Then sending message should be 2

Given a messenger
When set server to edu and message to some message
Then sending message should be 2

Given a messenger
When set server to edu and message to ab
Then sending message should be 2

Given a messenger
When set server to inf.ug.edu.pl and message to not set
Then sending message should be 2

Given a messenger
When set server to not set and message to not set
Then sending message should be 2


