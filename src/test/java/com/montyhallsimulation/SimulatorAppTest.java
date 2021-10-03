package com.montyhallsimulation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SimulatorAppTest {
	
	@Autowired
	SimulatorApp simulator;
	
	@Test
	public void testSwitch()
	{
	    String simulationResult = simulator.simulator(10, "switch");
	    Assert.assertNotNull(simulationResult);
	}
	
	@Test
	public void testStick()
	{
	    String simulationResult = simulator.simulator(100, "stick");
	    Assert.assertNotNull(simulationResult);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testInvlidSimulation()
	{
	    simulator.simulator(null, "stick");
	}
}
