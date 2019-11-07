package com;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.logging.Logger;

public class Controller implements ActionListener
{
	public Model _model;
	public View _view;
	private MonitorView _monitorView;
	private ConsumerTopic _consumer;
	private RequestTable _pinTable;

	public Controller(Model m, View v, MonitorView monitorView, ConsumerTopic consumer, RequestTable requestTable)
	{
		this._model = m;
		this._view = v;
		this._monitorView = monitorView;
		this._consumer = consumer;
		_pinTable = requestTable;
	}
	
	public void enterCommand()
	{
		Integer val = _model.checkPass();
		Integer key = _pinTable.addRecordNextKeyReturn(val);
		_consumer.askForAccess(key, val);
	}
	
	public void checkAccess()
	{
		if(!_consumer.getAccessStateSet())
		{
			_view.displayPassMessage("Loading...");
			if(_consumer.getAccessState() && _pinTable.doesKeyExist(_consumer.getId()))
			{
				_view.displayPassMessage("Pass");
				this._monitorView.setMonitor();
			}
			else
			{
				_view.displayErrorMessage("Wrong Passcode");
			}
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent e)
	{
		if(Types.Actions.ENTER.name().equals(e.getActionCommand()))
		{
			this.enterCommand();
			this.checkAccess();
			_view.setDigits(_model.initModel(Types.RESET));	
		}
		else if(Types.Actions.ADD_D1.name().equals(e.getActionCommand()))
		{
			_view.setDigit1(_model.incrementValue(Types.D1));
		}
		else if(Types.Actions.SUB_D1.name().equals(e.getActionCommand()))
		{
			_view.setDigit1(_model.decrementValue(Types.D1));
		}
		else if(Types.Actions.ADD_D2.name().equals(e.getActionCommand()))
		{
			_view.setDigit2(_model.incrementValue(Types.D2));
		}
		else if(Types.Actions.SUB_D2.name().equals(e.getActionCommand()))
		{
			_view.setDigit2(_model.decrementValue(Types.D2));
		}
		else if(Types.Actions.ADD_D3.name().equals(e.getActionCommand()))
		{
			_view.setDigit3(_model.incrementValue(Types.D3));
		}
		else if(Types.Actions.SUB_D3.name().equals(e.getActionCommand()))
		{
			_view.setDigit3(_model.decrementValue(Types.D3));
		}
		else if(Types.Actions.ADD_D4.name().equals(e.getActionCommand()))
		{
			_view.setDigit4(_model.incrementValue(Types.D4));
		}
		else if(Types.Actions.SUB_D4.name().equals(e.getActionCommand()))
		{
			_view.setDigit4(_model.decrementValue(Types.D4));
		}
		else if(Types.State.OFF.name().equals(e.getActionCommand()))
		{
			_monitorView.setMonitorState(_model.setModelStateOFF());
			_monitorView.close();
		}
		else if(Types.State.ON.name().equals(e.getActionCommand()))
		{
			_monitorView.setMonitorState(_model.setModelStateOn());
			_monitorView.close();
		}
	}

	public void initmodel(int x, String state)
	{
		_view.setDigits(_model.initModel(x));
		_monitorView.setMonitorState(state);
	}
}