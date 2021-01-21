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
import fakeEnrollments from './fake-enrollments';
import EnrollmentRightDetails from 'app/shared/model/domain/dto/enrollment-right-details';
import mapEnrollmentRightDetailsToEnrollmentData from './domain/mapper/enrollment-right-details-to-enrollment-data-mapper';
import axios from 'axios';
import log from 'app/config/log';
import EnrollmentData from './enrollment-data';

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
    log.info('going to enrollment:');
    log.info(enrollmentData);
  }

  renderHeader() {
    return (
      <Typography variant='h4' component='h4' align='center'>
        Semestry z prawem do zapisu
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
              <TableCell align="right">Kierunek</TableCell>
              <TableCell align="right">Semestr</TableCell>
              <TableCell align="right">Specjalizacja</TableCell>
              <TableCell align="right">Początek</TableCell>
              <TableCell align="right">Koniec</TableCell>
              <TableCell align="right">Prawo</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {this.state.enrollments.map((row) => (
              <Row key={row.id} row={row} onSelected={this.goToEnrollment} />
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
