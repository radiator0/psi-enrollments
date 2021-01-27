import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import CheckCircleIcon from '@material-ui/icons/CheckCircle'
import BlockIcon from '@material-ui/icons/Block'
import PersonAddIcon from '@material-ui/icons/PersonAdd'
import RemoveCircleIcon from '@material-ui/icons/RemoveCircle'
import LiveHelpIcon from '@material-ui/icons/LiveHelp'
import SettingsBackupRestoreIcon from '@material-ui/icons/SettingsBackupRestore'
import { recurringScheduleElementToShortDateString } from '../../../../shared/model/domain/mapper/recurring-schedule-element-to-date-string';
import log from 'app/config/log';
import GroupsData from '../../groups-data';
import { EnrollingAction } from '../../enrolling-action';
import { IconButton } from '@material-ui/core';
import green from '@material-ui/core/colors/green';
import red from '@material-ui/core/colors/red';
import blue from '@material-ui/core/colors/blue';
import InfoIcon from '@material-ui/icons/Info';
import { NavLink } from 'react-router-dom';

const useRowStyles = makeStyles({
    root: {
        '& > *': {
            borderBottom: 'unset',
        },
    },
});

const renderCanEnrollOverLimit = (row: GroupsData) => {
    return row.canEnrollOverLimit ? <CheckCircleIcon style={{ color: green[500] }} ></CheckCircleIcon> : <BlockIcon style={{ color: red[900] }}></BlockIcon>
}

const renderSchedule = (row: GroupsData) => {
    if(row.schedules.length === 0) {
        return '';
    }
    const shortDates = row.schedules.map(s => recurringScheduleElementToShortDateString(s));
    return shortDates.reduce((previousValue, currentValue, index) => {
        return `${previousValue}${index > 0 ? ', ' : ''}${currentValue}`;
    });
}

const renderLecturer = (row: GroupsData) => {
    return `${row.lecturerTitle && `${row.lecturerTitle} ` ||  ' '}${row.lecturerFirstName && `${row.lecturerFirstName} ` || ' '}${row.lecturerSecondName && `${row.lecturerSecondName} ` || ' '}${row.lecturerLastName || ' '}`
}

const getActionButton = (row: GroupsData, action: EnrollingAction) => {
    switch (action) {
        case EnrollingAction.AskOverLimit:
            return (<LiveHelpIcon style={{ color: blue[500] }}></LiveHelpIcon>);
        case EnrollingAction.RecallAsk:
            return (<SettingsBackupRestoreIcon style={{ color: blue[500] }}></SettingsBackupRestoreIcon>);
        case EnrollingAction.Enroll:
            return (<PersonAddIcon ></PersonAddIcon>);
        case EnrollingAction.Disenroll:
            return (<RemoveCircleIcon style={{ color: red[900] }}></RemoveCircleIcon>);
        default:
            return (<></>);
    }
}

const renderActionButton = (row: GroupsData, action: EnrollingAction, onSelected: (group: GroupsData, action: EnrollingAction) => void) => {
    return (
        <IconButton aria-label="expand row" size="small" onClick={() => onSelected(row, action)}>
            {getActionButton(row, action)}
        </IconButton>
    );
}

const renderInfoButton = (groupCode) => {
    return (<IconButton component={NavLink} to={"/group-details/"+ groupCode}><InfoIcon/></IconButton>)
}

export type IRowProps = {
    row: GroupsData,
    action: EnrollingAction,
    onSelected: (group: GroupsData, action: EnrollingAction) => void
};;

function Row(props: IRowProps) {
    const { row, action, onSelected } = props;
    const classes = useRowStyles();
    log.info(props);
    return (
        <React.Fragment>
            <TableRow className={classes.root}>
                <TableCell align="right">
                    {row.groupCode}
                    {renderInfoButton(row.groupCode)}
                </TableCell>
                <TableCell align="right">{`${row.enrolledCount}/${row.limit}`}</TableCell>
                <TableCell align="center">{renderCanEnrollOverLimit(row)}</TableCell>
                <TableCell align="right">{renderSchedule(row)}</TableCell>
                <TableCell align="right">{renderLecturer(row)}</TableCell>
                <TableCell align="right">{renderActionButton(row, action, onSelected)}</TableCell>
            </TableRow>
        </React.Fragment>
    );
}

Row.propTypes = {
    row: PropTypes.shape({
        groupCode: PropTypes.string.isRequired,
        enrolledCount: PropTypes.number.isRequired,
        limit: PropTypes.number.isRequired,
        isStudentEnrolled: PropTypes.bool.isRequired,
        canEnrollOverLimit: PropTypes.bool.isRequired,
        lecturerTitle: PropTypes.string,
        lecturerFirstName: PropTypes.string,
        lecturerSecondName: PropTypes.string,
        lecturerLastName: PropTypes.string,
        schedules: PropTypes.arrayOf(PropTypes.any),
    }).isRequired,
};

export default Row;