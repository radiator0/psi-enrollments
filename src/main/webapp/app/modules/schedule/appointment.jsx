import * as React from 'react';
import IconButton from '@material-ui/core/IconButton';
import LockIcon from '@material-ui/icons/Lock';
import LockOpenIcon from '@material-ui/icons/LockOpen';
import { Typography } from '@material-ui/core';
import { withStyles } from '@material-ui/core/styles';
import { Appointments } from '@devexpress/dx-react-scheduler-material-ui';
import GroupState from './group-state';

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

const stateChangeButton = (classes = {}, data = {}, toggleVisibility = () => {}, onAppointmentMetaChange = () => {}) => {
  if(data.state === GroupState.Irrelevant) {
    return (<></>);
  };

  return (
    <IconButton
      className={classes.button}
      onClick={({ target }) => {
        toggleVisibility();
        onAppointmentMetaChange({ target, data });
      }}
    >
      {data.state === GroupState.Blocked && <LockIcon fontSize="small" />}
      {data.state === GroupState.Unlocked && <LockOpenIcon fontSize="small" />}
    </IconButton>
  );
};

const lecturer = (data = {}) => {
  return `${data.room || ''} ${data.building || ''}`;
};

const place = (data = {}) => {
  return `${data.lecturerTitle || ''} ${data.lecturerFirstName || ''} ${data.lecturerLastName || ''}`;
};

const AppointmentBase = ({
  children,
  data,
  onClick,
  classes,
  toggleVisibility,
  onAppointmentMetaChange,
  ...restProps
}) => (
  <Appointments.Appointment
    {...restProps}
  >
    <React.Fragment>
      {stateChangeButton(classes, data, toggleVisibility, onAppointmentMetaChange)}
      {children}
      <Typography className={classes.text}>
        {lecturer(data)}
      </Typography>
      <Typography className={classes.text}>
        {place(data)}
      </Typography>
    </React.Fragment>
  </Appointments.Appointment>
);
  
  const Appointment = withStyles(styles, { name: 'Appointment' })(AppointmentBase);
  
  export default Appointment;