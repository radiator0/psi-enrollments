import { green, red, blue } from '@material-ui/core/colors';
import { CheckCircleIcon } from '@material-ui/data-grid';
import { recurringScheduleElementToShortDateString } from 'app/shared/model/domain/mapper/recurring-schedule-element-to-date-string';
import React from 'react';
import { NavLink } from 'react-router-dom';
import { EnrollingAction } from '../../enrolling-action';
import GroupsData from '../../groups-data';
import BlockIcon from '@material-ui/icons/Block';
import PersonAddIcon from '@material-ui/icons/PersonAdd';
import RemoveCircleIcon from '@material-ui/icons/RemoveCircle';
import LiveHelpIcon from '@material-ui/icons/LiveHelp';
import SettingsBackupRestoreIcon from '@material-ui/icons/SettingsBackupRestore';
import { IconButton } from '@material-ui/core';
import InfoIcon from '@material-ui/icons/Info';

const renderCanEnrollOverLimit = (row: GroupsData) => {
  return row.canEnrollOverLimit ? (
    <CheckCircleIcon style={{ color: green[500] }}></CheckCircleIcon>
  ) : (
    <BlockIcon style={{ color: red[900] }}></BlockIcon>
  );
};

const renderSchedule = (row: GroupsData) => {
  if (row.schedules.length === 0) {
    return '';
  }
  const shortDates = row.schedules.map(s => recurringScheduleElementToShortDateString(s));
  return shortDates.reduce((previousValue, currentValue, index) => {
    return `${previousValue} ${index > 1 ? ', ' : ''} ${currentValue}`;
  });
};

// const renderLecturer = (row: GroupsData) => {
//   return `${row.lecturerTitle && ' '}${row.lecturerFirstName && ' '}${row.lecturerSecondName && ' '}${row.lecturerLastName && ' '}`;
// };
//

const getActionButton = (row: GroupsData, action: EnrollingAction) => {
  switch (action) {
    case EnrollingAction.AskOverLimit:
      return <LiveHelpIcon style={{ color: blue[500] }}></LiveHelpIcon>;
    case EnrollingAction.RecallAsk:
      return <SettingsBackupRestoreIcon style={{ color: blue[500] }}></SettingsBackupRestoreIcon>;
    case EnrollingAction.Enroll:
      return <PersonAddIcon></PersonAddIcon>;
    case EnrollingAction.Disenroll:
      return <RemoveCircleIcon style={{ color: red[900] }}></RemoveCircleIcon>;
    default:
      return <></>;
  }
};

const renderActionButton = (row: GroupsData, action: EnrollingAction, onSelected: (group: GroupsData, action: EnrollingAction) => void) => {
  return (
    <IconButton aria-label="expand row" size="small" onClick={() => onSelected(row, action)}>
      {getActionButton(row, action)}
    </IconButton>
  );
};

const renderInfoButton = groupCode => {
  return (
    <IconButton component={NavLink} to={'/group-details/' + groupCode}>
      <InfoIcon />
    </IconButton>
  );
};
