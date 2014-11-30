  /////////////////////New from previous version///////////////
        PauseButton.setBounds(85, 404, 89, 23);
        PauseButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            { if(e.getActionCommand().equals("Pause")) {
            	cp.pause();
            	PauseButton.setText("Resume");
            }else{
            	cp.play(Speed);
            	PauseButton.setText("Pause");
            }
            
            }
        });;
        add(PauseButton);
        /////////////////////////////////////////////////////////////////
