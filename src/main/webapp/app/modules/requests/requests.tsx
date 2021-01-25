import React, { Component } from 'react';
import { connect } from 'react-redux';
import Table from '@material-ui/core/Table';
import { Translate, TextFormat, IPaginationBaseState, logInfo } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import KeyboardArrowDownIcon from '@material-ui/icons/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@material-ui/icons/KeyboardArrowUp';
import { IRootState } from 'app/shared/reducers';
import { getEntities, deleteEntity, acceptEntity, declineEntity } from 'app/shared/reducers/request.reducer';
import { Box, ButtonGroup, Collapse, Container, createMuiTheme, IconButton, MuiThemeProvider, Paper, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography } from '@material-ui/core';
import { APP_LOCAL_DATE_FORMAT, AUTHORITIES } from 'app/config/constants';
import { Button } from '@material-ui/core';
import { green, red } from '@material-ui/core/colors';
import { faCheck, faTimes } from '@fortawesome/free-solid-svg-icons';

const theme = createMuiTheme({ palette: { primary: red, secondary: green } })

export interface IRequestsProps extends StateProps, DispatchProps { }

export interface IRequestsStates {
  open: boolean[],
}

class Requests extends Component<IRequestsProps, IRequestsStates> {

  paginationState: IPaginationBaseState;

  constructor(props) {
    super(props);
    props.getEntities();
    this.state = {
      open: [],
    }
  }

  render() {
    const { requestList, loading, account } = this.props;
    const { open } = this.state;
    return (
      <React.Fragment>
        {requestList && requestList.length > 0 ? (
          <TableContainer component={Paper}>
            <Table aria-label="table">
              <TableHead>
                <TableRow>
                  <TableCell />
                  <TableCell>
                    <Translate contentKey="enrollmentsApp.request.date">Date</Translate>
                  </TableCell>
                  <TableCell>
                    <Translate contentKey="enrollmentsApp.request.isExamined">Is Examined</Translate>
                  </TableCell>
                  <TableCell>
                    <Translate contentKey="enrollmentsApp.request.isAccepted">Is Accepted</Translate>
                  </TableCell>
                  <TableCell>
                    <Translate contentKey="enrollmentsApp.request.classGroup">Class Group</Translate>
                  </TableCell>
                  <TableCell>
                    <Translate contentKey="enrollmentsApp.request.student">Student</Translate>
                  </TableCell>
                  <TableCell />
                </TableRow>
              </TableHead>
              <TableBody>
                {requestList.map((request, i) => (
                  <React.Fragment key={`entity-${i}`}>
                    <TableRow>
                      <TableCell>
                        <IconButton aria-label="expand row" size="small" onClick={() => { open[i] = !this.state.open[i]; this.setState({ open }) }}>
                          {this.state.open[i] ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                        </IconButton>
                      </TableCell>
                      <TableCell>{request.date ? <TextFormat type="date" value={request.date} format={APP_LOCAL_DATE_FORMAT} /> : null}</TableCell>
                      <TableCell>{request.isExamined ? 'true' : 'false'}</TableCell>
                      <TableCell>{request.isAccepted ? 'true' : 'false'}</TableCell>
                      <TableCell>{request.classGroupCode}</TableCell>
                      <TableCell>{request.studentName}</TableCell>
                      <TableCell className="text-right">
                        <MuiThemeProvider theme={theme}>
                          {account.authorities[0] === AUTHORITIES.LECTURER ?
                            <ButtonGroup variant="contained" color="primary">
                              <Button
                                onClick={() => this.props.declineEntity(request.id)}
                                startIcon={<FontAwesomeIcon icon={faTimes} />}
                                color="primary"
                                variant="contained">
                                <Translate contentKey="enrollmentsApp.action.deny">Deny</Translate>
                              </Button>
                              <Button
                                onClick={() => this.props.acceptEntity(request.id)}
                                startIcon={<FontAwesomeIcon icon={faCheck} />}
                                color="secondary"
                                variant="text">
                                <Translate contentKey="enrollmentsApp.action.accept">Accept</Translate>
                              </Button>
                            </ButtonGroup>
                            : account.authorities[0] === AUTHORITIES.STUDENT ?
                              <Button
                                onClick={() => this.props.deleteEntity(request.id)}
                                startIcon={<FontAwesomeIcon icon="trash" />}
                                color="primary">
                                <Translate contentKey="entity.action.delete">Delete</Translate>
                              </Button>
                              :
                              null
                          }
                        </MuiThemeProvider>
                      </TableCell>
                    </TableRow>
                    <TableRow>
                      <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
                        <Collapse in={this.state.open[i]} timeout="auto" unmountOnExit>
                          <Box margin={1}>
                            <Typography variant="h6" gutterBottom component="div">
                              <Translate contentKey="enrollmentsApp.request.text">Request</Translate>
                            </Typography>
                            <Typography variant="subtitle1">{request.text}</Typography>
                          </Box>
                        </Collapse>
                      </TableCell>
                    </TableRow>
                  </React.Fragment>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        ) : (
            !loading && (
              <Container className="alert alert-warning">
                <Translate contentKey="enrollmentsApp.request.home.notFound">No Requests found</Translate>
              </Container>
            )
          )}
      </React.Fragment>
    );
  }
}

const mapStateToProps = ({ request, authentication }: IRootState) => ({
  requestList: request.entities,
  loading: request.loading,
  totalItems: request.totalItems,
  account: authentication.account,
});

const mapDispatchToProps = {
  getEntities,
  deleteEntity,
  acceptEntity,
  declineEntity,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Requests);
