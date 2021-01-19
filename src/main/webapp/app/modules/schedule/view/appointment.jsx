import * as React from 'react';
import { Typography } from '@material-ui/core';
import { withStyles } from '@material-ui/core/styles';
import { Appointments } from '@devexpress/dx-react-scheduler-material-ui';

const styles = theme => ({
    button: {
      color: theme.palette.background.default,
      padding: 0,
    },
    text: {
      marginTop: -5,
      paddingLeft: theme.spacing(1),
      paddingRight: theme.spacing(1),
      overflow: 'hidden',
      textOverflow: 'ellipsis',
      whiteSpace: 'nowrap',
      color: 'white',
      fontSize: 12,
    },
  });

const lecturer = (data = {}) => {
  return `${data.lecturerTitle || ''} ${data.lecturerFirstName || ''} ${data.lecturerLastName || ''}`;
};

const place = (data = {}) => {
  return `${data.room || ''} ${data.building || ''}`;
};

const AppointmentBase = ({
  children,
  appointmentData,
  classes,
  ...restProps
}) => (
  <Appointments.Appointment
    {...restProps}
  >
    <React.Fragment>
      {children}
      <Typography className={classes.text}>
        {lecturer(appointmentData)}
      </Typography>
      <Typography className={classes.text}>
        {place(appointmentData)}
      </Typography>
    </React.Fragment>
  </Appointments.Appointment>
);
  
  const Appointment = withStyles(styles, { name: 'Appointment' })(AppointmentBase);
  
  export default Appointment;