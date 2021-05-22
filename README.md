# Foreground Service start optimisation
A sample to show optimisations in starting a foreground service
<br/>
A common crash related to usage of foreground services is : 
Context.startForegroundService() did not then call Service.startForeground()
<br/>
<p>
To get rid of this use this strategy : <br/>
Instead of starting the service as a foreground service, 
start the service as a background service, bind to it,
then when you have the service instance available in your 
activity/UI component, you can directly call a method inside 
the service which calls Service.startForeground() and adds the notification. 
</p>
This might sound like a hack but think about how music apps like Spotify start their service. This is a similar approach. 
The results are outstanding. Using this approach the issue count reduced to 0  from a significant unrevealable number.
