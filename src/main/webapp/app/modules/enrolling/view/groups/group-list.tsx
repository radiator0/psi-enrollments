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
import { CellClassParams, CellParams, ColDef, Columns, DataGrid, ValueFormatterParams } from '@material-ui/data-grid';
import { APP_DATE_FORMAT_DOT } from 'app/config/constants';
import moment from 'moment';

export type IGroupListProps = {
  enrollment: EnrollmentData;
  groupsData: Array<GroupsData>;
  requestOverLimit: readonly IRequest[];
  onSelected: (group: GroupsData, action: EnrollingAction) => void;
};

// eslint-disable-next-line @typescript-eslint/no-empty-interface
interface IGroupListState {}

class GroupList extends Component<IGroupListProps, IGroupListState> {
  constructor(props: IGroupListProps) {
    super(props);

    this.state = {
      coursesData: [],
    };
  }

  componentDidMount() {
    console.error(this.props.groupsData);
  }

  renderHeader(currentDate: Date) {
    return (
      <Typography variant="h4" style={{ textAlign: 'center' }}>
        <Translate contentKey={'enrolling.header.groups'}>Grupy</Translate>
      </Typography>
    );
  }

  getColumns(): ColDef[] {
    return [
      {
        field: 'groupCode',
        headerName: translate('enrolling.group.code'),
        flex: 1,
      },
      {
        field: 'enrolled',
        headerName: translate('enrolling.group.enrolled'),
        flex: 1,
      },
      {
        field: 'overLimit',
        headerName: translate('enrolling.group.overLimit'),
        flex: 1,
      },
      {
        field: 'schedule',
        headerName: translate('enrolling.group.schedule'),
        flex: 1,
      },
      {
        field: 'lecturer',
        headerName: translate('enrolling.group.lecturer'),
        flex: 1,
      },
      {
        field: 'action',
        headerName: translate('enrolling.group.action'),
        flex: 1,
        renderCell: (params: ValueFormatterParams) => <p></p>,
      },
    ];
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

  renderGroupsRows(currentDate: Date) {
    const { groupsData, enrollment, requestOverLimit, onSelected } = this.props;
    return (
      <>
        <div style={{ height: '500px' }}>
          <DataGrid
            rows={groupsData}
            columns={this.getColumns()}
            localeText={translate('dataGrid')}
            disableSelectionOnClick={true}
            hideFooter={true}
          />
        </div>
        {/* <Table aria-label="table">
              <TableHead>
                <TableRow>
                  <TableCell align="right"><Translate contentKey={'enrolling.group.code'}>Group code</Translate></TableCell>
                  <TableCell align="right"><Translate contentKey={'enrolling.group.enrolled'}>Enrolled</Translate></TableCell>
                  <TableCell align="center"><Translate contentKey={'enrolling.group.overLimit'}>Over limit</Translate></TableCell>
                  <TableCell align="right"><Translate contentKey={'enrolling.group.schedule'}>Schedule</Translate></TableCell>
                  <TableCell align="right"><Translate contentKey={'enrolling.group.lecturer'}>Lecturer</Translate></TableCell>
                  <TableCell align="right"><Translate contentKey={'enrolling.group.action'}>Action</Translate></TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {groupsData.map((row) => (
                // eslint-disable-next-line @typescript-eslint/ban-ts-ignore
                // @ts-ignore
                <Row key={row.id} row={row} action={this.determinePossibleActionForGroup(row, enrollment, currentDate, requestOverLimit.find(ask => ask.classGroupId === row.id))} onSelected={onSelected} />
            ))}
              </TableBody>
            </Table> */}
      </>
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
