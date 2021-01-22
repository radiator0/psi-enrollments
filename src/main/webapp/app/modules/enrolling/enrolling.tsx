import React, { Component } from 'react';
import { connect } from 'react-redux';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import CourseList from './view/course-list';
import axios from 'axios';
import log from 'app/config/log';

export type IEnrollingProps = StateProps;


interface IEnrollingState {
  enrollments: Array<any>
};

const gridStyle = {
  display: 'flex',
};

const courseListStyle = {
  width: '25%',
};

const groupsListStyle = {
  width: '75%', 
};

class Enrolling extends Component<IEnrollingProps, IEnrollingState> {
  constructor(props: IEnrollingProps) {
    super(props);

    this.state = {
      enrollments: []
    };
  }

  componentDidMount() {
    this.getData();
  };

  getData() {
    /* axios.get<Array<EnrollmentRightDetails>>("/api/enrollment-right")
    .then(r => {
      log.info(r.data);
      const data = r.data.map(element => mapEnrollmentRightDetailsToEnrollmentData(element));
      this.setState({ enrollments: data })
    })
    .catch(e => log.error(e))*/
  }

  renderHeader() {
    return (
      <Typography variant='h4' component='h4' align='center'>
        Zapisy
      </Typography>
    );
  }

  renderCoursesList() {
    return (
        <CourseList></CourseList>
    );
  }

  renderGroupsTable() {
    return (
      <TableContainer component={Paper}>
        <Table aria-label="collapsible table">
          <TableHead>
            <TableRow>
              <TableCell />
              <TableCell align="right">Kod grupy</TableCell>
              <TableCell align="right">Zapisani</TableCell>
              <TableCell align="right">Ponad stan</TableCell>
              <TableCell align="right">Forma</TableCell>
              <TableCell align="right">Termin</TableCell>
              <TableCell align="right">Prowadzący</TableCell>
              <TableCell align="right">Akcja</TableCell>
            </TableRow>
          </TableHead>
        </Table>
      </TableContainer>
    );
  }

  render() {
    return (
      <>
        {this.renderHeader()}
        <Grid style={gridStyle}>
          <div style={courseListStyle}>
            {this.renderCoursesList()}
          </div>
          <div style={groupsListStyle}>
            {this.renderGroupsTable()}
          </div>
        </Grid>
      </>
    );
  }
};

const mapStateToProps = () => ({
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Enrolling);
