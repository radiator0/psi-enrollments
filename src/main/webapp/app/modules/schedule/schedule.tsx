import * as React from 'react';
import Paper from '@material-ui/core/Paper';
import { ViewState } from '@devexpress/dx-react-scheduler';
import {
  Scheduler, WeekView, Appointments, CurrentTimeIndicator, Resources, Toolbar, DateNavigator, TodayButton, AppointmentTooltip,
} from '@devexpress/dx-react-scheduler-material-ui';
import ScheduleData from './schedule-data';
import axios from 'axios';
import mapScheduleElementToScheduleData from './domain/mapper/schedule-element-to-data-mapper';
import mapRecurringScheduleElementToScheduleData from './domain/mapper/recurring-schedule-element-to-data-mapper';
import ScheduleElement from './domain/dto/schedule-element';
import RecurringScheduleElement from './domain/dto/recurring-schedule-element';
import scheduleResources from './schedule-resources';
import log from '../../config/log';
import Appointment from './appointment';
import { Typography } from '@material-ui/core';
import { RouteComponentProps } from 'react-router-dom';
import ScheduleType from './schedule-types'
import { StaticContext } from 'react-router';
import Header from './appointment-header';
import Content from './appointment-content';
import CommandButton from './appointment-command-button';

interface IScheduleState {
  scheduleType: string,
  data: Array<ScheduleData>;
  resources: Array<any>;
  currentDate: Date;
};


export class Schedule extends React.PureComponent<RouteComponentProps<{ scheduleType: string }>, IScheduleState> {
  constructor(props: Readonly<RouteComponentProps<{ scheduleType: string; }, StaticContext, unknown>>) {
    super(props);

    this.state = {
      scheduleType: this.props.match.params.scheduleType,
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
    if(this.state.scheduleType === ScheduleType.week)
    {
      axios.get<Array<ScheduleElement>>("/api/week-schedule")
      .then(r => {
        const data = r.data.map(element => mapScheduleElementToScheduleData(element));
        this.setState( { data })
      })
      .catch(e => log.error(e))
    }
    else
    {
      axios.get<Array<RecurringScheduleElement>>("/api/semester-schedule")
      .then(r => {
        const data = r.data.map(element => mapRecurringScheduleElementToScheduleData(element));
        this.setState( { data })
      })
      .catch(e => log.error(e))
    }
  }

  customAppointment(props) {
    return (
      <Appointment
        {...props}
        appointmentData={props.data}
      />
    );
  }
  
  semesterHeading() {
    const semester = 'x';
    return this.state.scheduleType === ScheduleType.semester && (
      <Typography>
        {`Semestr ${semester}`}
      </Typography>
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
            // eslint-disable-next-line @typescript-eslint/ban-ts-ignore
            // @ts-ignore
            onClick={log.info('dupa')}
            appointmentComponent={this.customAppointment}
          />
          <AppointmentTooltip
            // eslint-disable-next-line @typescript-eslint/ban-ts-ignore
            // @ts-ignore
            headerComponent={Header}
            // eslint-disable-next-line @typescript-eslint/ban-ts-ignore
            // @ts-ignore
            contentComponent={Content}
            // eslint-disable-next-line @typescript-eslint/ban-ts-ignore
            // @ts-ignore
            commandButtonComponent={CommandButton}
            showCloseButton
          />
					<CurrentTimeIndicator
              shadePreviousCells={true}
              shadePreviousAppointments={true}
              updateInterval={10000}
            />
          <Resources
            data={resources}
          />
          {this.semesterHeading()}
          {this.state.scheduleType === ScheduleType.week && <Toolbar />}
          {this.state.scheduleType === ScheduleType.week && <DateNavigator />}
          {this.state.scheduleType === ScheduleType.week && <TodayButton />}         
        </Scheduler>
      </Paper>
    );
  }
}

export default Schedule;
