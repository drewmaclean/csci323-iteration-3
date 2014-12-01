///////////////////////////NEW/////////////////////
    static int UserChoose=5;
    ///////////////////////////END////////////////////////
    
    
     ////////////////////////NEW//////////////////////////////
    public int UserChooseMovingDate(int nwChoice){
    	UserChoose=nwChoice;
    	return UserChoose;
    }
    public void update() {
    	
    	
    	
    	currentPrice = data.get(i);
        transientNonCompressedDates.add(dates.get(i));
        transientNonCompressedData.add(data.get(i));

        transientCompressedDates.add(dates.get(i));
        transientCompressedData.add(data.get(i));
        
        /////////Place UserChoose in while loop//////////////////////
        while(transientCompressedData.size()>UserChoose){
            transientCompressedData.remove(0);
            transientCompressedDates.remove(0);
        }

        i--;
    }
    ////////////////////////////END/////////////////////////////////// 
