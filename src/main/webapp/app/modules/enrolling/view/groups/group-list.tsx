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
import Row, { renderLecturer, renderSchedule } from './row';
import GroupsData from '../../groups-data';
import { EnrollingAction } from '../../enrolling-action';
import EnrollmentData from '../../../../modules/enrollments/enrollment-data';
import isEnrollmentAtive from '../../../../shared/model/domain/util/is-enrollment-active';
import log from 'app/config/log';
import { translate, Translate } from 'react-jhipster';
import { IRequest } from 'app/shared/model/request.model';
import { Checkbox, FormControlLabel, FormGroup, Grid, IconButton, InputAdornment, Select, TextField } from '@material-ui/core';
import { red } from '@material-ui/core/colors';
import QueryBuilderIcon from '@material-ui/icons/QueryBuilder';
import { group } from 'console';
import ClearIcon from '@material-ui/icons/Clear';

export type IGroupListProps = {
  enrollment: EnrollmentData;
  groupsData: Array<GroupsData>;
  requestOverLimit: readonly IRequest[];
  onSelected: (group: GroupsData, action: EnrollingAction) => void;
};

// eslint-disable-next-line @typescript-eslint/no-empty-interface
interface IGroupListState {
  time: string;
  lecturer: string;
  schedule: string;
  selectEnrolled: string;
  selectOverLimit: string;
  groupCode: string;
}

class GroupList extends Component<IGroupListProps, IGroupListState> {
  constructor(props) {
    super(props);
    this.state = {
      time: new Date().toLocaleTimeString(),
      lecturer: '',
      schedule: '',
      selectEnrolled: '',
      selectOverLimit: '',
      groupCode: '',
    };
  }

  componentDidMount() {
    setInterval(() => {
      this.setState({
        time: new Date().toLocaleTimeString(),
      });
    }, 1000);
  }

  handleChange = (event: React.ChangeEvent<{ name?: string; value: unknown }>) => {
    const name = event.target.name;
    this.setState({
      ...this.state,
      [name]: event.target.value,
    });
  };

  clear = () => {
    this.setState({
      ...this.state,
      lecturer: '',
      schedule: '',
      selectEnrolled: '',
      selectOverLimit: '',
      groupCode: '',
    });
  };

  handleChangeLecturer = (event: React.ChangeEvent<HTMLInputElement>) => {
    this.setState({
      lecturer: event.target.value,
    });
  };

  handleChangeSchedule = (event: React.ChangeEvent<HTMLInputElement>) => {
    this.setState({
      schedule: event.target.value,
    });
  };

  handleChangeGroupCode = (event: React.ChangeEvent<HTMLInputElement>) => {
    this.setState({
      groupCode: event.target.value,
    });
  };

  renderHeader(currentDate: Date) {
    return (
      <>
        <Typography variant="h4" style={{ textAlign: 'center', paddingBottom: '10px' }}>
          <Translate contentKey={'enrolling.header.groups'}>Grupy</Translate>
        </Typography>
      </>
    );
  }

  determinePossibleActionForGroup(groupsData: GroupsData, enrollment: EnrollmentData, currentDate: Date, requestOverLimit: IRequest) {
    if (!isEnrollmentAtive(enrollment, currentDate)) {
      return EnrollingAction.NoAction;
    }
    if (requestOverLimit?.id && !requestOverLimit?.isExamined){
      return EnrollingAction.RecallAsk;
    }
    if (groupsData.isStudentEnrolled) {
      return EnrollingAction.Disenroll;
    }
    if (!groupsData.isLimitReached) {
      return EnrollingAction.Enroll;
    }
    if (groupsData.canEnrollOverLimit && !requestOverLimit?.id) {
      return EnrollingAction.AskOverLimit;
    }
    return EnrollingAction.NoAction;
  }

  getGroupsData() {
    let filteredGroups = this.props.groupsData;
    if (this.state.selectEnrolled !== '') {
      if (this.state.selectEnrolled === 'full') {
        filteredGroups = filteredGroups.filter(groupData => groupData.isLimitReached);
      } else if (this.state.selectEnrolled === 'free') {
        filteredGroups = filteredGroups.filter(groupData => !groupData.isLimitReached);
      }
    }
    if (this.state.selectOverLimit !== '') {
      if (this.state.selectOverLimit === 'yes') {
        filteredGroups = filteredGroups.filter(groupData => groupData.canEnrollOverLimit);
      } else if (this.state.selectOverLimit === 'no') {
        filteredGroups = filteredGroups.filter(groupData => !groupData.canEnrollOverLimit);
      }
    }
    if (this.state.lecturer !== '') {
      filteredGroups = filteredGroups.filter(groupData => {
        return renderLecturer(groupData).toLowerCase().includes(this.state.lecturer.toLowerCase());
      });
    }
    if (this.state.schedule !== '') {
      filteredGroups = filteredGroups.filter(groupData => {
        return renderSchedule(groupData).toLowerCase().includes(this.state.schedule.toLowerCase());
      });
    }
    if (this.state.groupCode !== '') {
      filteredGroups = filteredGroups.filter(groupData => {
        return groupData.groupCode.toLowerCase().includes(this.state.groupCode.toLowerCase());
      });
    }
    return filteredGroups;
  }

  renderGroupsRows(currentDate: Date) {
    const { enrollment, requestOverLimit, onSelected } = this.props;
    const { lecturer, schedule, selectEnrolled, selectOverLimit, groupCode } = this.state;
    const filteredGroups = this.getGroupsData();
    return (
      <TableContainer component={Paper}>
        <Table aria-label="table">
          <TableHead>
            <TableRow>
              <TableCell align="right">
                <Translate contentKey={'enrolling.group.code'}>Group code</Translate>
                <br />
                <Grid container>
                  <Grid item xs={3}>
                    {lecturer !== '' || schedule !== '' || selectEnrolled !== '' || selectOverLimit !== '' || groupCode !== '' ? (
                      <IconButton size="small" style={{ marginRight: '10px' }}>
                        <ClearIcon fontSize="small" onClick={() => this.clear()}></ClearIcon>
                      </IconButton>
                    ) : null}
                  </Grid>
                  <Grid item xs={9}>
                    <TextField id="standard-basic" size="small" value={this.state.groupCode} onChange={this.handleChangeGroupCode} />
                  </Grid>
                </Grid>
              </TableCell>
              <TableCell align="right">
                <Translate contentKey={'enrolling.group.enrolled'}>Enrolled</Translate>
                <br />
                <Select
                  native
                  value={this.state.selectEnrolled}
                  onChange={this.handleChange}
                  inputProps={{
                    name: 'selectEnrolled',
                    id: 'age-native-simple',
                  }}
                >
                  <option aria-label="None" value="" />
                  <option value={'free'}>{translate('enrolling.filters.free')}</option>
                  <option value={'full'}>{translate('enrolling.filters.full')}</option>
                </Select>
              </TableCell>
              <TableCell align="center">
                <Translate contentKey={'enrolling.group.overLimit'}>Over limit</Translate>
                <br />
                <Select
                  native
                  value={this.state.selectOverLimit}
                  onChange={this.handleChange}
                  inputProps={{
                    name: 'selectOverLimit',
                    id: 'age-native-simple',
                  }}
                >
                  <option aria-label="None" value="" />
                  <option value={'yes'}>{translate('enrolling.filters.yes')}</option>
                  <option value={'no'}>{translate('enrolling.filters.no')}</option>
                </Select>
              </TableCell>
              <TableCell align="right">
                <Translate contentKey={'enrolling.group.schedule'}>Schedule</Translate>
                <br />
                <TextField id="standard-basic" size="small" value={this.state.schedule} onChange={this.handleChangeSchedule} />
              </TableCell>
              <TableCell align="right">
                <Translate contentKey={'enrolling.group.lecturer'}>Lecturer</Translate>
                <br />
                <TextField id="standard-basic" size="small" value={this.state.lecturer} onChange={this.handleChangeLecturer} />
              </TableCell>
              <TableCell align="right">
                <TextField
                  id="time"
                  type="time"
                  inputProps={{
                    step: 1,
                    style: {
                      color: !isEnrollmentAtive(enrollment, currentDate) ? red[500] : 'black',
                    },
                  }}
                  value={this.state.time}
                  disabled={true}
                  InputProps={{
                    startAdornment: (
                      <InputAdornment position="end">
                        <QueryBuilderIcon
                          style={{ paddingBottom: '2px', color: !isEnrollmentAtive(enrollment, currentDate) ? red[500] : 'black' }}
                        />
                      </InputAdornment>
                    ),
                  }}
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
