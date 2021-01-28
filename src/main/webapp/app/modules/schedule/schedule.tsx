import './schedule.scss';
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
import ScheduleElement from '../../shared/model/domain/dto/schedule-element';
import RecurringScheduleElement from '../../shared/model/domain/dto/recurring-schedule-element';
import csr from './schedule-resources';
import log from '../../config/log';
import Appointment from './view/appointment';
import { Button, Grid, Typography } from '@material-ui/core';
import { RouteComponentProps } from 'react-router-dom';
import ScheduleType from './schedule-types'
import { StaticContext } from 'react-router';
import { connect } from 'react-redux';
import Header from './view/appointment-header';
import Content from './view/appointment-content';
import CommandButton from './view/appointment-command-button';
import { Translate, translate} from 'react-jhipster';
import downloadFile from 'js-file-download';
import { toast } from 'react-toastify';

interface IScheduleState {
  data: Array<ScheduleData>;
  createScheduleResources: () => Array<any>;
  currentDate: Date;
};

interface ISCheduleProps extends StateProps, RouteComponentProps<{ scheduleType: string }> {};

export class Schedule extends React.PureComponent<ISCheduleProps, IScheduleState> {
  constructor(props: Readonly<RouteComponentProps<{ scheduleType: string; }, StaticContext, unknown>>) {
    super(props);

    this.state = {
      data: [/*
        new ScheduleData(1, new Date('2021-01-18T07:30'), new Date('2021-01-18T09:00'), 'BAZY', ClassType.Laboratory.toString()),
        new ScheduleData(2, new Date('2021-01-18T09:15'), new Date('2021-01-18T11:00'), 'TEŻ BAZY ALE NA CZERWONO', ClassType.Lecture.toString()),
      */],
      createScheduleResources: csr,
      currentDate: new Date(),

    };
  }

  currentDateChange = (currentDate: Date) => { this.setState({ currentDate }); };

  getData = () => {
    if (this.props.match.params.scheduleType === ScheduleType.week) {
      axios.get<Array<ScheduleElement>>("/api/week-schedule")
        .then(r => {
          const data = r.data.map(element => mapScheduleElementToScheduleData(element));
          this.setState({ data })
        })
        .catch(e => log.error(e))
    }
    else {
      axios.get<Array<RecurringScheduleElement>>("/api/semester-schedule")
        .then(r => {
          const data = r.data.map(element => mapRecurringScheduleElementToScheduleData(element));
          this.setState({ data })
        })
        .catch(e => log.error(e))
    }
  }
  
  componentDidMount() {
    this.getData();
  }

  componentDidUpdate(prevProps) {
    if (prevProps.match.params.scheduleType !== this.props.match.params.scheduleType) {
      this.setState( { currentDate: new Date() } )
      this.getData();
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

  renderHeader() {
    return (<>
        <Grid container justify="flex-end">
          <Button variant="contained" color="primary" onClick={this.downloadCalendar} style={{marginRight: 20}}>
            <Translate contentKey='schedule.header.exportCalendar'>Export</Translate>
          </Button>
        </Grid>
        {this.props.match.params.scheduleType === ScheduleType.semester && (
          <Typography variant="h4" component="h4" align='center'>
            <Translate contentKey={'schedule.header.semester'}>Semester</Translate>
          </Typography>
        )}
      </>);
  }

  downloadCalendar() {
    axios.get<any>("/api/schedule/ica", { headers: { ['schedule-language']: translate('schedule.locale') }, responseType: 'blob' })
    .then(r => {
      if(r.data.type !== 'application/octet-stream') {  // HACK xD
        throw Error('Invalid data format');
      }
      downloadFile(r.data, `calendar_${translate('schedule.locale')}.ics`);
    })
    .catch(e => {
      log.error(e);
      toast.error(translate("schedule.notification.exportFailure"));
    })
  }

  render() {
    const { data, createScheduleResources, currentDate } = this.state;
    const { scheduleType } = this.props.match.params;
    log.info(data);
    return (
      <Paper className={scheduleType}>
        <Scheduler
          data={data}
          height={800}
          locale={translate('schedule.locale')}
        >
          <ViewState
            currentDate={currentDate}
            onCurrentDateChange={this.currentDateChange}
          />
          <WeekView
            startDayHour={7.5}
            endDayHour={20.5}
            excludedDays={[0, 6]}
            cellDuration={30}
          />
          <Appointments
            // eslint-disable-next-line @typescript-eslint/ban-ts-ignore
            // @ts-ignore
            onClick={ }
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
            data={createScheduleResources()}
          />
          {this.renderHeader()}
          {scheduleType === ScheduleType.week && <Toolbar />}
          {scheduleType === ScheduleType.week && <DateNavigator />}
          {scheduleType === ScheduleType.week && <TodayButton messages={{ today: translate('schedule.todayButtonText') }} />}
        </Scheduler>
      </Paper>
    );
  }
}

const mapStateToProps = () => ({
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Schedule);
