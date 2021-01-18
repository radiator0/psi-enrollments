import * as React from 'react';
import Paper from '@material-ui/core/Paper';
import { ViewState } from '@devexpress/dx-react-scheduler';
import {
  Scheduler, WeekView, Appointments, CurrentTimeIndicator, Resources, Toolbar, DateNavigator, TodayButton
} from '@devexpress/dx-react-scheduler-material-ui';
import ScheduleData from './schedule-data';
import axios from 'axios';
import mapScheduleElementToScheduleData from './schedule-element-to-data-mapper';
import ScheduleElement from './schedule-element';
import scheduleResources from './schedule-resources';
import log from '../../config/log';
import Appointment from './appointment';
import ClassType from './class-type';

interface IScheduleProps {
  semester: number;
};

interface IScheduleState {
  data: Array<ScheduleData>;
  resources: Array<any>;
  currentDate: Date;
};


export class Schedule extends React.PureComponent<IScheduleProps, IScheduleState> {
  constructor(props) {
    super(props);

    this.state = {
      data: [/*
        new ScheduleData(1, new Date('2021-01-18T07:30'), new Date('2021-01-18T09:00'), 'BAZY', ClassType.Laboratory.toString()),
        new ScheduleData(2, new Date('2021-01-18T09:15'), new Date('2021-01-18T11:00'), 'TEŻ BAZY ALE NA CZERWONO', ClassType.Lecture.toString()),
      */],
      resources: scheduleResources,
      currentDate: new Date(),
    };
  }
  
  currentDateChange = (currentDate : Date) => { this.setState({ currentDate }); };

  componentDidMount() {
    axios.get<Array<ScheduleElement>>("/api/my-schedule")
    .then(r => {
      const data = r.data.map(element => mapScheduleElementToScheduleData(element));
      this.setState( { data })
    })
    .catch(e => log.error(e))
  }

  myAppointment(props) {
    return (
      <Appointment
        {...props}
      />
    );
  }

  render() {
    const { data, resources, currentDate } = this.state;

    return (
      <Paper>
        <Scheduler
					data={data}
					height={800}
        >
          <ViewState
            currentDate={currentDate}
            onCurrentDateChange={this.currentDateChange}
          />
          <WeekView
            startDayHour={7.5}
            endDayHour={20.5}
            excludedDays={[0,6]}
            cellDuration={30}
          />
          <Appointments
            appointmentComponent={this.myAppointment}
          />
					<CurrentTimeIndicator
              shadePreviousCells={true}
              shadePreviousAppointments={true}
              updateInterval={10000}
            />
          <Resources
            data={resources}
          />
          <Toolbar />
          <DateNavigator />
          <TodayButton />
        </Scheduler>
      </Paper>
    );
  }
}

export default Schedule;
