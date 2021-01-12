import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IStudyProgram, defaultValue } from 'app/shared/model/study-program.model';

export const ACTION_TYPES = {
  FETCH_STUDYPROGRAM_LIST: 'studyProgram/FETCH_STUDYPROGRAM_LIST',
  FETCH_STUDYPROGRAM: 'studyProgram/FETCH_STUDYPROGRAM',
  CREATE_STUDYPROGRAM: 'studyProgram/CREATE_STUDYPROGRAM',
  UPDATE_STUDYPROGRAM: 'studyProgram/UPDATE_STUDYPROGRAM',
  DELETE_STUDYPROGRAM: 'studyProgram/DELETE_STUDYPROGRAM',
  RESET: 'studyProgram/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IStudyProgram>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type StudyProgramState = Readonly<typeof initialState>;

// Reducer

export default (state: StudyProgramState = initialState, action): StudyProgramState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_STUDYPROGRAM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_STUDYPROGRAM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_STUDYPROGRAM):
    case REQUEST(ACTION_TYPES.UPDATE_STUDYPROGRAM):
    case REQUEST(ACTION_TYPES.DELETE_STUDYPROGRAM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_STUDYPROGRAM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_STUDYPROGRAM):
    case FAILURE(ACTION_TYPES.CREATE_STUDYPROGRAM):
    case FAILURE(ACTION_TYPES.UPDATE_STUDYPROGRAM):
    case FAILURE(ACTION_TYPES.DELETE_STUDYPROGRAM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_STUDYPROGRAM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_STUDYPROGRAM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_STUDYPROGRAM):
    case SUCCESS(ACTION_TYPES.UPDATE_STUDYPROGRAM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_STUDYPROGRAM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/study-programs';

// Actions

export const getEntities: ICrudGetAllAction<IStudyProgram> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_STUDYPROGRAM_LIST,
  payload: axios.get<IStudyProgram>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IStudyProgram> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_STUDYPROGRAM,
    payload: axios.get<IStudyProgram>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IStudyProgram> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_STUDYPROGRAM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IStudyProgram> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_STUDYPROGRAM,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IStudyProgram> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_STUDYPROGRAM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
