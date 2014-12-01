	//////////////////////NEW//////////////////////////////
	static ArrayList<Date> FakeDates= new ArrayList<Date>();
	static ArrayList<Double> FakeDouble= new ArrayList<Double>();
	static final int SlideMIN = 0;
	static final int SlideMAX = 50;
	static final int SlideINIT = 5; 
	JSlider MovingWindowsSlider = new JSlider(JSlider.HORIZONTAL,
            SlideMIN, SlideMAX, SlideINIT);
	Stock stockV1;
	///////////////////////END////////////////////////////
	
	/////////////NEW///////////////////////////
    	//Add fake data to get out of errors
    	for(int i=0;i<=15;i++){
    		Calendar cal = Calendar.getInstance();
    		cal.set(2011, 5+i, 10);
    		FakeDates.add(cal.getTime());
    		FakeDouble.add(i*2.3);
    	}
    	//new instance of stock class to access its methods
    	stockV1 = new Stock("BLAH",FakeDates,FakeDouble);
    	stockV1.UserChooseMovingDate(5);
    	//Setup slider
    	MovingWindowsSlider.setMinorTickSpacing(5);
    	MovingWindowsSlider.setMajorTickSpacing(10);
    	MovingWindowsSlider.setPaintTicks(true);
    	MovingWindowsSlider.setPaintLabels(true);
    	MovingWindowsSlider.setLabelTable(MovingWindowsSlider.createStandardLabels(10));
    	MovingWindowsSlider.setBounds(15, 30, 150, 40);
    	MovingWindowsSlider.addChangeListener(new ChangeListener(){
    		@Override
    		public void stateChanged( ChangeEvent event )
    	{
    		
    		if( event.getSource() == MovingWindowsSlider);
    		{
    			stockV1.UserChooseMovingDate(MovingWindowsSlider.getValue());
    			
    		}
    	}
    	});;
    	add(MovingWindowsSlider);
    	JLabel MovingWindowlbl = new JLabel("Moving Window:");
        MovingWindowlbl.setBounds(15, 10, 150, 20);
        add(MovingWindowlbl);
    	/////////////////////////////END/////////////////////////////
    	
