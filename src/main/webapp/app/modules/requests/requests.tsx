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
import { faCheck, faTimes, faUndoAlt } from '@fortawesome/free-solid-svg-icons';
import { CheckCircleIcon } from '@material-ui/data-grid';
import BlockIcon from '@material-ui/icons/Block';
import { Alert, AlertTitle } from '@material-ui/lab';

const theme = createMuiTheme({ palette: { primary: red, secondary: { main: green[500] } } })

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
    const isStudent = account.authorities[0] === AUTHORITIES.STUDENT;
    const isLecturer = account.authorities[0] === AUTHORITIES.LECTURER;
    let list = requestList;
    if(isLecturer) {
      list = requestList.filter(r => !r.isExamined).sort((r1, r2) => Date.parse(r1.date) - Date.parse(r2.date));
    }
    return (
      <React.Fragment>
        <Typography variant='h4' component='h4' align='center'>
          <Translate contentKey={'enrollmentsApp.request.home.title'}>Semesters with enrollment right</Translate>
        </Typography>
        {list && list.length > 0 ? (
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
                  {isLecturer ?
                    <TableCell>
                      <Translate contentKey="enrollmentsApp.request.student">Student</Translate>
                    </TableCell>
                    :
                    isStudent ?
                      <TableCell>
                        <Translate contentKey="enrollmentsApp.request.lecturer">Lecturer</Translate>
                      </TableCell>
                      : null}
                  <TableCell />
                </TableRow>
              </TableHead>
              <TableBody>
                {list.map((req, i) => (
                  <React.Fragment key={`entity-${i}`}>
                    <TableRow>
                      <TableCell>
                        <IconButton aria-label="expand row" size="small" onClick={() => { open[i] = !this.state.open[i]; this.setState({ open }) }}>
                          {this.state.open[i] ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                        </IconButton>
                      </TableCell>
                      <TableCell>{req.date ? <TextFormat type="date" value={req.date} format={APP_LOCAL_DATE_FORMAT} /> : null}</TableCell>
                      <TableCell>{req.isExamined ? <CheckCircleIcon style={{ color: green[500] }} ></CheckCircleIcon> : <BlockIcon style={{ color: red[900] }}></BlockIcon>}</TableCell>
                      <TableCell>{req.isAccepted ? <CheckCircleIcon style={{ color: green[500] }} ></CheckCircleIcon> : <BlockIcon style={{ color: red[900] }}></BlockIcon>}</TableCell>
                      <TableCell>{req.classGroupCode}</TableCell>
                      {isLecturer ?
                        <TableCell>{req.studentName}</TableCell>
                        :
                        isStudent ?
                          <TableCell>{req.lecturerName}</TableCell>
                          : null}
                      <TableCell className="text-right">
                        <MuiThemeProvider theme={theme}>
                          {isLecturer ?
                            <ButtonGroup variant="contained" color="primary" disabled={req.isExamined}>
                              <Button
                                onClick={() => this.props.declineEntity(req.id)}
                                startIcon={<FontAwesomeIcon icon={faTimes} />}
                                color="primary"
                                variant="contained">
                                <Translate contentKey="enrollmentsApp.action.deny">Deny</Translate>
                              </Button>
                              <Button
                                onClick={() => this.props.acceptEntity(req.id)}
                                startIcon={<FontAwesomeIcon icon={faCheck} />}
                                color="secondary"
                                variant="text">
                                <Translate contentKey="enrollmentsApp.action.accept">Accept</Translate>
                              </Button>
                            </ButtonGroup>
                            : isStudent && !req.isExamined?
                              <Button
                                onClick={() => this.props.deleteEntity(req.id)}
                                startIcon={<FontAwesomeIcon icon={faUndoAlt} />}
                                style={{color:'rgb(183, 28, 28)'}}>
                                <Translate contentKey="enrollmentsApp.action.recall">Recall</Translate>
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
                            <span style={{ fontFamily: 'inherit', whiteSpace: "pre-wrap", wordWrap: "break-word" }}>
                              {req.text}
                            </span>
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
              <Alert severity="info">
                <AlertTitle>Info</AlertTitle>
                <Translate contentKey="enrollmentsApp.request.home.notFound">No not examined requests found</Translate>
              </Alert>
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
