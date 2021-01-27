/* eslint-disable no-console */
import * as React from 'react';
import { AppointmentTooltip } from '@devexpress/dx-react-scheduler-material-ui';
import Grid from '@material-ui/core/Grid';
import Room from '@material-ui/icons/Room';
import DateRangeIcon from '@material-ui/icons/DateRange';
import CalendarTodayIcon from '@material-ui/icons/CalendarToday';
import Person from '@material-ui/icons/Person';
import { withStyles } from '@material-ui/core/styles';
import { translate } from 'react-jhipster';
import { weekTypeToLongString } from '../../../shared/model/domain/mapper/week-type-to-string-mapper';
import { semesterPeriodToLongString } from '../../../shared/model/domain/mapper/semester-period-to-string-mapper';
import WeekType from '../../../shared/model/domain/enum/week-type';
import SemesterPeriod from '../../../shared/model/domain/enum/semester-period';

const style = ({ palette }) => ({
  textCenter: {
    textAlign: 'center',
  },
});

const weekType = (data = {}, classes = {}) => {
  if(data.weekType === WeekType.All) {
    return (<></>);
  }
  return (
    <>
      <Grid item xs={2} className={classes.textCenter}>
        <DateRangeIcon className={classes.icon} />
      </Grid>
      <Grid item xs={10}>
        <span>{`${translate('schedule.appointment.weekType')}: ${weekTypeToLongString(data.weekType)}`}</span>
      </Grid>
    </>
  );
};

const semesterPeriod = (data = {}, classes = {}) => {
  if(data.semesterPeriod === SemesterPeriod.Whole) {
    return (<></>);
  }
  return (
    <>
      <Grid item xs={2} className={classes.textCenter}>
        <CalendarTodayIcon className={classes.icon} />
      </Grid>
      <Grid item xs={10}>
        <span>{`${translate('schedule.appointment.semesterPeriod')}: ${semesterPeriodToLongString(data.semesterPeriod)}`}</span>
      </Grid>
    </>
  );
}

const lecturer = (data = {}) => {
  return `${data.lecturerTitle || ''} ${data.lecturerFirstName || ''} ${data.lecturerLastName || ''}`;
};

const place = (data = {}) => {
  return `${data.room || ''} ${data.building || ''}`;
};

const scheduleDescription = (classes, appointmentData) => {
  if(!appointmentData.weekType || !appointmentData.semesterPeriod)
    return (<></>);
  return (
    <>
      {weekType(appointmentData, classes)}
      {semesterPeriod(appointmentData, classes)}
    </>
  );
}

const lecturerDescription = (classes, appointmentData) => {
  if(!appointmentData.lecturer)
    return (<></>);
  return (
    <>
      <Grid item xs={2} className={classes.textCenter}>
        <Person className={classes.icon} />
      </Grid>
      <Grid item xs={10}>
        <span>{lecturer(appointmentData)}</span>
      </Grid>
    </>
  );
}

const placeDescription = (classes, appointmentData) => {
  if(!appointmentData.room || !appointmentData.building)
    return (<></>);
  return (
    <>
      <Grid item xs={2} className={classes.textCenter}>
        <Room className={classes.icon} />
      </Grid>
      <Grid item xs={10}>
        <span>{place(appointmentData)}</span>
      </Grid>
    </>
  );
}

const Content = withStyles(style, { name: 'Content' })(({
  children, appointmentData, classes, ...restProps
}) => (
  <AppointmentTooltip.Content {...restProps} appointmentData={appointmentData}>
    <Grid container alignItems="center">
      {scheduleDescription(classes, appointmentData)}
      {lecturerDescription(classes, appointmentData)}
      {placeDescription(classes, appointmentData)}
    </Grid>
  </AppointmentTooltip.Content>
));

export default Content;