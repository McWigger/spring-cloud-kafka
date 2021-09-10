package csci318.demo.binder;

import csci318.demo.binder.binding.BoundName;
import csci318.demo.binder.binding.InOutBound;
import csci318.demo.model.Appliance;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(InOutBound.class)
public class Consumer {
    @StreamListener(BoundName.INBOUND)
    public void consume(Appliance appliance) {
        // The logic of this streaming app goes here...
        System.out.println(appliance.toString());
    }

}