import { Badge, Button, IconButton, ListItem, ListItemIcon, ListItemText, Tooltip } from '@material-ui/core';
import React, { Component } from 'react';
import TodayIcon from '@material-ui/icons/Today';
import ScheduleIcon from '@material-ui/icons/Schedule';
import { NavLink as RouterLink } from 'react-router-dom';
import { logInfo, Translate, translate } from 'react-jhipster';
import AssignmentIcon from '@material-ui/icons/Assignment';
import RateReviewIcon from '@material-ui/icons/RateReview';
import HelpOutlinedIcon from '@material-ui/icons/HelpOutlined';
import HomeIcon from '@material-ui/icons/Home';
import ImportContactsIcon from '@material-ui/icons/ImportContacts';
import { connect } from 'react-redux';
import { IRootState } from 'app/shared/reducers';

export const HomeMenu = () => (
  <ListItem button exact={true} component={RouterLink} activeClassName="nav-active" to="/">
    <Tooltip title={translate('global.menu.home')}>
      <ListItemIcon>
        <HomeIcon />
      </ListItemIcon>
    </Tooltip>
    <ListItemText primary={translate('global.menu.home')} />
  </ListItem>
);

export const ScheduleWeekMenu = () => (
  <ListItem button component={RouterLink} activeClassName="nav-active" to="/schedule/week">
    <Tooltip title={translate('global.menu.scheduleWeek')}>
      <ListItemIcon>
        <TodayIcon />
      </ListItemIcon>
    </Tooltip>
    <ListItemText primary={translate('global.menu.scheduleWeek')} />
  </ListItem>
);

export const ScheduleSemesterMenu = () => (
  <ListItem button component={RouterLink} activeClassName="nav-active" to="/schedule/semester">
    <Tooltip title={translate('global.menu.scheduleSemester')}>
      <ListItemIcon>
        <ScheduleIcon />
      </ListItemIcon>
    </Tooltip>
    <ListItemText primary={translate('global.menu.scheduleSemester')} />
  </ListItem>
);

export const EnrollmentsMenu = () => (
  <ListItem button component={RouterLink} activeClassName="nav-active" to="/enrollments">
    <Tooltip title={translate('global.menu.enrollments')}>
      <ListItemIcon>
        <Badge badgeContent={12} color="primary">
          <AssignmentIcon />
        </Badge>
      </ListItemIcon>
    </Tooltip>
    <ListItemText primary={translate('global.menu.enrollments')} />
  </ListItem>
);

export interface LecturerAsksProps {
  notExaminedCount: number
}

export const LecturerAsks = (props: LecturerAsksProps) => (
  <ListItem button component={RouterLink} activeClassName="nav-active" to="/requests">
    <Tooltip title={translate('global.menu.requests')}>
      <ListItemIcon>
        <Badge badgeContent={props.notExaminedCount} color="primary">
          <RateReviewIcon />
        </Badge>
      </ListItemIcon>
    </Tooltip>
    <ListItemText primary={translate('global.menu.requests')} />
  </ListItem>
);

export interface StudentAsksProps {
  notExaminedCount: number
}

export const StudentAsks = (props: StudentAsksProps) => (

  <ListItem button component={RouterLink} activeClassName="nav-active" to="/requests">
    <Tooltip title={translate('global.menu.requests')}>
      <ListItemIcon>
        <Badge badgeContent={props.notExaminedCount} color="primary">
          <HelpOutlinedIcon />
        </Badge>
      </ListItemIcon>
    </Tooltip>
    <ListItemText primary={translate('global.menu.requests')} />
  </ListItem>
)

export const SwaggerMenu = () => (
  <ListItem button component={RouterLink} activeClassName="nav-active" to="/docs">
    <Tooltip title={'Swagger'}>
      <ListItemIcon>
        <ImportContactsIcon />
      </ListItemIcon>
    </Tooltip>
    <ListItemText primary={'Swagger'} />
  </ListItem>
);

export const LoginItem = () => (
  <Button color="inherit" component={RouterLink} to="/login">
    <Translate contentKey="global.menu.account.login">Login</Translate>
  </Button>
);
