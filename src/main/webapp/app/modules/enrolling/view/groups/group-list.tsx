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



export type IGroupListProps = {
    enrollment: EnrollmentData,
    groupsData: Array<GroupsData>,
    onSelected: (group: GroupsData, action: EnrollingAction) => void
};;


// eslint-disable-next-line @typescript-eslint/no-empty-interface
interface IGroupListState {
};

class GroupList extends Component<IGroupListProps, IGroupListState> {
    constructor(props: IGroupListProps) {
        super(props);

        this.state = {
            coursesData: [],
        };
    }

    componentDidMount() {
    };

    renderHeader(currentDate: Date) {
        return (
            <Typography variant='h4' style={{textAlign: 'center'}}>
                Grupy
            </Typography>
        );
    }

    determinePossibleActionForGroup(groupsData: GroupsData, enrollment: EnrollmentData, currentDate: Date) {
        if(!isEnrollmentAtive(enrollment, currentDate)) {
            log.info(1);
            return EnrollingAction.NoAction;
        }
        if(groupsData.isStudentEnrolled) {
            return EnrollingAction.Disenroll;
        }
        if(!groupsData.isLimitReached) {
            return EnrollingAction.Enroll;
        }
        if(groupsData.requestOverLimit) {
            return EnrollingAction.RecallAsk;
        }
        if(groupsData.canEnrollOverLimit) {
            return EnrollingAction.AskOverLimit;
        }
        return EnrollingAction.NoAction;
    }

    renderGroupsRows(currentDate: Date) {
        const { groupsData, enrollment, onSelected } = this.props;
        return (
            <TableContainer component={Paper}>
            <Table aria-label="collapsible table">
              <TableHead>
                <TableRow>
                  <TableCell align="right">Kod grupy</TableCell>
                  <TableCell align="right">Zapisani</TableCell>
                  <TableCell align="center">Ponad stan</TableCell>
                  <TableCell align="right">Termin</TableCell>
                  <TableCell align="right">Prowadzący</TableCell>
                  <TableCell align="right">Akcja</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {groupsData.map((row) => (
                // eslint-disable-next-line @typescript-eslint/ban-ts-ignore
                // @ts-ignore
                <Row key={row.id} row={row} action={this.determinePossibleActionForGroup(row, enrollment, currentDate)} onSelected={onSelected} />
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
};

const mapStateToProps = () => ({
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(GroupList);
