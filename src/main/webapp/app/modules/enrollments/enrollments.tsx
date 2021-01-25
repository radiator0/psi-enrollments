import React, { Component } from 'react';
import { connect } from 'react-redux';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import Row from './view/row';
import EnrollmentRightDetails from 'app/shared/model/domain/dto/enrollment-right-details';
import mapEnrollmentRightDetailsToEnrollmentData from './domain/mapper/enrollment-right-details-to-enrollment-data-mapper';
import axios from 'axios';
import log from 'app/config/log';
import EnrollmentData from './enrollment-data';
import { Translate } from 'react-jhipster';

export type IEnrollmentsProps = StateProps;


interface IEnrollmentsState {
  enrollments: Array<EnrollmentData>
};


class Enrollments extends Component<IEnrollmentsProps, IEnrollmentsState> {
  constructor(props: IEnrollmentsProps) {
    super(props);

    this.state = {
      enrollments: []
    };
  }

  componentDidMount() {
    this.getData();
  };

  getData() {
    axios.get<Array<EnrollmentRightDetails>>("/api/enrollment-right")
    .then(r => {
      log.info(r.data);
      const data = r.data.map(element => mapEnrollmentRightDetailsToEnrollmentData(element));
      this.setState({ enrollments: data })
    })
    .catch(e => log.error(e))
  }

  goToEnrollment(enrollmentData : EnrollmentData) {
    // eslint-disable-next-line @typescript-eslint/ban-ts-ignore
    // @ts-ignore
    this.props.history.push( { pathname: `/enrolling`, state: { enrollment: enrollmentData }});
  }

  renderHeader() {
    return (
      <Typography variant='h4' component='h4' align='center'>
        <Translate contentKey={'enrollments.header.semesterWithRights'}>Semesters with enrollment right</Translate>
      </Typography>
    );
  }

  renderEnrollmentsTable() {
    return (
      <TableContainer component={Paper}>
        <Table aria-label="collapsible table">
          <TableHead>
            <TableRow>
              <TableCell />
              <TableCell align="right"><Translate contentKey={'enrollments.right.fieldOfStudy'}>Field of study</Translate></TableCell>
              <TableCell align="right"><Translate contentKey={'enrollments.right.semester'}>Semester</Translate></TableCell>
              <TableCell align="right"><Translate contentKey={'enrollments.right.speciality'}>Speciality</Translate></TableCell>
              <TableCell align="right"><Translate contentKey={'enrollments.right.enrollmentsStart'}>Start</Translate></TableCell>
              <TableCell align="right"><Translate contentKey={'enrollments.right.enrollmentsEnd'}>End</Translate></TableCell>
              <TableCell align="right"><Translate contentKey={'enrollments.right.rightStart'}>Right</Translate></TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {this.state.enrollments.map((row) => (
              // eslint-disable-next-line @typescript-eslint/ban-ts-ignore
              // @ts-ignore
              <Row key={row.id} row={row} onSelected={this.goToEnrollment.bind(this)} />
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    );
  }

  render() {
    return (
      <>
        {this.renderHeader()}
        {this.renderEnrollmentsTable()}
      </>
    );
  }
};

const mapStateToProps = () => ({
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Enrollments);
