package corrientazo.drones4n;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class OperatorWorkerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void start_exactCapacity() {
        //given
        final int capacity = 3;
        Drone drone = mock(Drone.class);
        when(drone.getCapacity()).thenReturn(capacity);
        List<String> instructions = Arrays.asList("inst1", "inst2", "inst3");
        OperatorWorker operatorWorker = new OperatorWorker(drone, instructions);
        OperatorWorker operatorWorkerSpy = spy(operatorWorker);
        doNothing().when(operatorWorkerSpy).applyMoves(any(Drone.class), anyList());

        //when
        operatorWorkerSpy.start();

        //then
        verify(drone).getCapacity();
        verify(operatorWorkerSpy).applyMoves(any(Drone.class), anyList());
        verify(drone).goToStartingPoint();

    }

    @Test
    public void start_doubleCapacity() {
        //given
        final int capacity = 3;
        Drone drone = mock(Drone.class);
        when(drone.getCapacity()).thenReturn(capacity);
        List<String> instructions = Arrays.asList("inst1", "inst2", "inst3", "inst4", "inst5", "inst6");
        OperatorWorker operatorWorker = new OperatorWorker(drone, instructions);
        OperatorWorker operatorWorkerSpy = spy(operatorWorker);
        doNothing().when(operatorWorkerSpy).applyMoves(any(Drone.class), anyList());

        //when
        operatorWorkerSpy.start();

        //then
        verify(drone).getCapacity();
        verify(operatorWorkerSpy, times(2)).applyMoves(any(Drone.class), anyList());
        verify(drone, times(2)).goToStartingPoint();

    }

    @Test
    public void start_exceededCapacityButNotExact() {
        //given
        final int capacity = 3;
        Drone drone = mock(Drone.class);
        when(drone.getCapacity()).thenReturn(capacity);
        List<String> instructions = Arrays.asList("inst1", "inst2", "inst3", "inst4", "inst5");
        OperatorWorker operatorWorker = new OperatorWorker(drone, instructions);
        OperatorWorker operatorWorkerSpy = spy(operatorWorker);
        doNothing().when(operatorWorkerSpy).applyMoves(any(Drone.class), anyList());

        //when
        operatorWorkerSpy.start();

        //then
        verify(drone).getCapacity();
        verify(operatorWorkerSpy, times(2)).applyMoves(any(Drone.class), anyList());
        verify(drone, times(2)).goToStartingPoint();

    }

    @Test
    public void start_lessCapacity() {
        //given
        final int capacity = 10;
        Drone drone = mock(Drone.class);
        when(drone.getCapacity()).thenReturn(capacity);
        List<String> instructions = Arrays.asList("inst1", "inst2", "inst3", "inst4", "inst5");
        OperatorWorker operatorWorker = new OperatorWorker(drone, instructions);
        OperatorWorker operatorWorkerSpy = spy(operatorWorker);
        doNothing().when(operatorWorkerSpy).applyMoves(any(Drone.class), anyList());

        //when
        operatorWorkerSpy.start();

        //then
        verify(drone).getCapacity();
        verify(operatorWorkerSpy).applyMoves(any(Drone.class), anyList());
        verify(drone).goToStartingPoint();

    }

    @Test
    public void applyMoves_movedOk() {
        // given
        Drone drone = mock(Drone.class);
        OperatorWorker operatorWorker = new OperatorWorker(drone, new ArrayList<>());

        List<String> insToApply = Arrays.asList("inst1", "inst2", "inst3");

        when(drone.getId()).thenReturn("1");
        when(drone.move(anyString())).thenReturn(true);

        //when
        operatorWorker.applyMoves(drone, insToApply);

        //then
        verify(drone, times(3)).move(anyString());

    }
}