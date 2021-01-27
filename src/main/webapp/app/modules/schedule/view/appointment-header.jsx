import * as React from 'react';
import Paper from '@material-ui/core/Paper';
import { AppointmentTooltip } from '@devexpress/dx-react-scheduler-material-ui';
import IconButton from '@material-ui/core/IconButton';
import MoreIcon from '@material-ui/icons/MoreVert';
import { withStyles } from '@material-ui/core/styles';
import classNames from 'clsx';
import UnfoldMoreIcon from '@material-ui/icons/UnfoldMore';
import { NavLink } from 'react-router-dom';

const style = ({ palette }) => ({
    icon: {
        color: palette.action.active,
    },
    header: {
        height: '55px',
        backgroundSize: 'cover',
    },
    thirdRoom: {
        background: 'url(https://yt3.ggpht.com/ytc/AAUvwngiK-oc-8zU4xmh7NH9pFW3v9gTVfvhPCaDZM2gVA=s900-c-k-c0x00ffffff-no-rj)',
    },
});

const getClassByLocation = (classes, location) => {
    return classes.thirdRoom;
};

const Header = withStyles(style, { name: 'Header' })(({
    children, appointmentData, classes, ...restProps
}) => (
        <AppointmentTooltip.Header
            {...restProps}
            className={classNames(getClassByLocation(classes, appointmentData.location), classes.header)}
            appointmentData={appointmentData}>
            {/* TOTO add groupcode here */}
            <IconButton component={NavLink} to={"/group-details/Z00-19a"}
                className={classes.commandButton}>
                <UnfoldMoreIcon />
            </IconButton>
            <IconButton
                /* eslint-disable-next-line no-alert */
                onClick={() => alert(JSON.stringify(appointmentData))}
                className={classes.commandButton}>
                <MoreIcon />
            </IconButton>
        </AppointmentTooltip.Header>
    ));

export default Header;