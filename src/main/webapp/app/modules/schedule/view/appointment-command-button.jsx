import * as React from 'react';
import { AppointmentTooltip } from '@devexpress/dx-react-scheduler-material-ui';
import { withStyles } from '@material-ui/core/styles';

const style = ({ palette }) => ({
    commandButton: {
      backgroundColor: 'rgba(255,255,255,0.65)',
    },
  });

  const CommandButton = withStyles(style, { name: 'CommandButton' })(({
    classes, ...restProps
  }) => (
    <AppointmentTooltip.CommandButton {...restProps} className={classes.commandButton} />
  ));

export default CommandButton;