/* eslint-disable no-console */
import * as React from 'react';
import { AppointmentTooltip } from '@devexpress/dx-react-scheduler-material-ui';
import Grid from '@material-ui/core/Grid';
import Room from '@material-ui/icons/Room';
import DateRange from '@material-ui/icons/DateRange';
import Person from '@material-ui/icons/Person';
import { withStyles } from '@material-ui/core/styles';

const style = ({ palette }) => ({
  textCenter: {
    textAlign: 'center',
  },
});

const schedule = (data = {}) => {
  return `WT: ${data.weekType || ''} | SH: ${data.semesterPeriod || ''}`;
};

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
      <Grid item xs={2} className={classes.textCenter}>
        <DateRange className={classes.icon} />
      </Grid>
      <Grid item xs={10}>
        <span>{schedule(appointmentData)}</span>
      </Grid>
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
  console.log(appointmentData);
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