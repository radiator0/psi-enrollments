import { Badge, Button, IconButton, ListItem, ListItemIcon, ListItemText, Tooltip } from '@material-ui/core';
import React from 'react';
import TodayIcon from '@material-ui/icons/Today';
import ScheduleIcon from '@material-ui/icons/Schedule';
import { Link as RouterLink } from 'react-router-dom';
import { Translate, translate } from 'react-jhipster';
import AssignmentIcon from '@material-ui/icons/Assignment';
import RateReviewIcon from '@material-ui/icons/RateReview';
import HelpOutlinedIcon from '@material-ui/icons/HelpOutlined';
import HomeIcon from '@material-ui/icons/Home';

export const HomeMenu = () => (
  <ListItem button component={RouterLink} to="/">
    <Tooltip title={translate('global.menu.home')}>
      <ListItemIcon>
        <HomeIcon />
      </ListItemIcon>
    </Tooltip>
    <ListItemText primary={translate('global.menu.home')} />
  </ListItem>
);

export const ScheduleWeekMenu = () => (
  <ListItem button component={RouterLink} to="/schedule/week">
    <Tooltip title={translate('global.menu.scheduleWeek')}>
      <ListItemIcon>
        <TodayIcon />
      </ListItemIcon>
    </Tooltip>
    <ListItemText primary={translate('global.menu.scheduleWeek')} />
  </ListItem>
);

export const ScheduleSemesterMenu = () => (
  <ListItem button component={RouterLink} to="/schedule/semester">
    <Tooltip title={translate('global.menu.scheduleSemester')}>
      <ListItemIcon>
        <ScheduleIcon />
      </ListItemIcon>
    </Tooltip>
    <ListItemText primary={translate('global.menu.scheduleSemester')} />
  </ListItem>
);

export const EnrollmentsMenu = () => (
  <ListItem button component={RouterLink} to="/enrollments">
    <Tooltip title={translate('global.menu.enrollments')}>
      <ListItemIcon>
        <Badge badgeContent={12} color="secondary">
          <AssignmentIcon />
        </Badge>
      </ListItemIcon>
    </Tooltip>
    <ListItemText primary={translate('global.menu.enrollments')} />
  </ListItem>
);

export const LecturerAsks = () => (
  <ListItem button component={RouterLink} to="/asks-for-enrollment-over-limit">
    <Tooltip title={translate('global.menu.asks-for-enrollment-over-limit')}>
      <ListItemIcon>
        <Badge variant="dot" color="secondary">
          <RateReviewIcon />
        </Badge>
      </ListItemIcon>
    </Tooltip>
    <ListItemText primary={translate('global.menu.asks-for-enrollment-over-limit')} />
  </ListItem>
);

export const StudentAsks = () => (
  <ListItem button component={RouterLink} to="/asks-for-enrollment-over-limit">
    <Tooltip title={translate('global.menu.asks-for-enrollment-over-limit')}>
      <ListItemIcon>
        <Badge variant="dot" color="primary">
          <HelpOutlinedIcon />
        </Badge>
      </ListItemIcon>
    </Tooltip>
    <ListItemText primary={translate('global.menu.asks-for-enrollment-over-limit')} />
  </ListItem>
);

export const LoginItem = () => (
  <Button color="inherit" component={RouterLink} to="/login">
    <Translate contentKey="global.menu.account.login">Login</Translate>
  </Button>
);
