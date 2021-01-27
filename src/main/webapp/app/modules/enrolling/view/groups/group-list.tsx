import React, { Component } from 'react';
import { connect } from 'react-redux';
import Table from '@material-ui/core/Table';
import Paper from '@material-ui/core/Paper';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import Row from './row';
import GroupsData from '../../groups-data';
import { EnrollingAction } from '../../enrolling-action';
import EnrollmentData from '../../../../modules/enrollments/enrollment-data';
import isEnrollmentAtive from '../../../../shared/model/domain/util/is-enrollment-active';
import log from 'app/config/log';
import { translate, Translate } from 'react-jhipster';
import { IRequest } from 'app/shared/model/request.model';
import { Checkbox, FormControlLabel, FormGroup, Grid, TextField } from '@material-ui/core';
import { red } from '@material-ui/core/colors';
import GroupData from '../../group-details/group-data';

export type IGroupListProps = {
  enrollment: EnrollmentData;
  groupsData: Array<GroupsData>;
  requestOverLimit: readonly IRequest[];
  onSelected: (group: GroupsData, action: EnrollingAction) => void;
};

// eslint-disable-next-line @typescript-eslint/no-empty-interface
interface IGroupListState {
  time: string;
  hideFull: boolean;
  hideNotOverLimit: boolean;
  filterLecturer: boolean;
  lecturer: string;
}

class GroupList extends Component<IGroupListProps, IGroupListState> {
  constructor(props) {
    super(props);
    this.state = {
      time: new Date().toLocaleTimeString(),
      hideFull: false,
      hideNotOverLimit: false,
      filterLecturer: false,
      lecturer: '',
    };
  }

  componentDidMount() {
    setInterval(() => {
      this.setState({
        time: new Date().toLocaleTimeString(),
      });
    }, 1000);
  }

  handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    this.setState({ ...this.state, [event.target.name]: event.target.checked });
  };

  handleChangeLecturer = (event: React.ChangeEvent<HTMLInputElement>) => {
    this.setState({
      lecturer: event.target.value,
    });
  };

  renderHeader(currentDate: Date) {
    return (
      <>
        <Typography variant="h4" style={{ textAlign: 'center', paddingBottom: '10px' }}>
          <Translate contentKey={'enrolling.header.groups'}>Grupy</Translate>
        </Typography>
        <FormGroup row style={{ paddingLeft: '140px' }}>
          <FormControlLabel
            style={{ marginBottom: '0' }}
            control={<Checkbox checked={this.state.hideFull} onChange={this.handleChange} name="hideFull" color="primary" />}
            label={translate('enrolling.filters.hideFullGroups')}
          />
          <FormControlLabel
            style={{ marginBottom: '0' }}
            control={
              <Checkbox checked={this.state.hideNotOverLimit} onChange={this.handleChange} name="hideNotOverLimit" color="primary" />
            }
            label={translate('enrolling.filters.hideNotOverLimit')}
          />
          <FormControlLabel
            style={{ marginBottom: '0' }}
            control={<Checkbox checked={this.state.filterLecturer} onChange={this.handleChange} name="filterLecturer" color="primary" />}
            label={translate('enrolling.filters.filterLecturer')}
          />
          {this.state.filterLecturer ? (
            <TextField
              id="standard-basic"
              label={translate('enrolling.filters.lecturer')}
              variant="outlined"
              size="small"
              value={this.state.lecturer}
              onChange={this.handleChangeLecturer}
            />
          ) : null}
        </FormGroup>
      </>
    );
  }

  determinePossibleActionForGroup(groupsData: GroupsData, enrollment: EnrollmentData, currentDate: Date, requestOverLimit: IRequest) {
    log.info(groupsData);
    if (!isEnrollmentAtive(enrollment, currentDate)) {
      return EnrollingAction.NoAction;
    }
    if (requestOverLimit?.id) {
      return EnrollingAction.RecallAsk;
    }
    if (groupsData.isStudentEnrolled) {
      return EnrollingAction.Disenroll;
    }
    if (!groupsData.isLimitReached) {
      return EnrollingAction.Enroll;
    }
    if (groupsData.canEnrollOverLimit) {
      return EnrollingAction.AskOverLimit;
    }
    return EnrollingAction.NoAction;
  }

  getGroupsData() {
    let filteredGroups = this.props.groupsData;
    if (this.state.hideFull) {
      filteredGroups = filteredGroups.filter(groupData => !groupData.isLimitReached);
    }
    if (this.state.hideNotOverLimit) {
      filteredGroups = filteredGroups.filter(groupData => groupData.canEnrollOverLimit);
    }
    if (this.state.filterLecturer && this.state.lecturer !== '') {
      filteredGroups = filteredGroups.filter(groupData => {
        const lecturer = `${groupData.lecturerTitle + ' '}${groupData.lecturerFirstName + ' '}${groupData.lecturerSecondName + ' '}${
          groupData.lecturerLastName + ' '
        }`;
        lecturer.includes(this.state.lecturer);
      });
    }
    return filteredGroups;
  }

  renderGroupsRows(currentDate: Date) {
    const { enrollment, requestOverLimit, onSelected } = this.props;
    const filteredGroups = this.getGroupsData();
    return (
      <TableContainer component={Paper}>
        <Table aria-label="table">
          <TableHead>
            <TableRow>
              <TableCell align="right">
                <Translate contentKey={'enrolling.group.code'}>Group code</Translate>
              </TableCell>
              <TableCell align="right">
                <Translate contentKey={'enrolling.group.enrolled'}>Enrolled</Translate>
              </TableCell>
              <TableCell align="center">
                <Translate contentKey={'enrolling.group.overLimit'}>Over limit</Translate>
              </TableCell>
              <TableCell align="right">
                <Translate contentKey={'enrolling.group.schedule'}>Schedule</Translate>
              </TableCell>
              <TableCell align="right">
                <Translate contentKey={'enrolling.group.lecturer'}>Lecturer</Translate>
              </TableCell>
              <TableCell align="right">
                <Translate contentKey={'enrolling.group.action'}>Action</Translate>
              </TableCell>
              <TableCell align="center">
                <TextField
                  id="time"
                  type="time"
                  inputProps={{
                    step: 1,
                    style: {
                      color: !isEnrollmentAtive(enrollment, currentDate) ? red[300] : 'black',
                    },
                  }}
                  value={this.state.time}
                  disabled={true}
                />
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {filteredGroups.map(row => (
              // eslint-disable-next-line @typescript-eslint/ban-ts-ignore
              // @ts-ignore
              <Row
                key={row.id}
                row={row}
                action={this.determinePossibleActionForGroup(
                  row,
                  enrollment,
                  currentDate,
                  requestOverLimit.find(ask => ask.classGroupId === row.id)
                )}
                onSelected={onSelected}
              />
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    );
  }

  render() {
    const currentDate = new Date();
    return (
      <>
        {this.renderHeader(currentDate)}
        {this.renderGroupsRows(currentDate)}
      </>
    );
  }
}

const mapStateToProps = () => ({});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(GroupList);
