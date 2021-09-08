# AndroidApps

Assumptions:

	- Application was built for Android v8.0+
	
	- "Consecutive clicks" is assumed to mean exactly 3 clicks
	  of Volume Down button, without any Volume Up clicks. For example
	  clicking Volume Down 2 times and then Volume Up once or more will
	  nulify the 2 Volume Down sequence.
	
Instructions:

	- At start the application will start in foreground. (I could not
	  preserve the state using a boolean variable.) Fixed.
	  
	- 3 clicks of Volume Down will send application to Foreground
	 (due to instruction above).
	 
	- 3 clicks Volume Down will send it to the Background from being
	  in the  Foreground.
	
