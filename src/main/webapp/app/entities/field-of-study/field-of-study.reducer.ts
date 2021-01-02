import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFieldOfStudy, defaultValue } from 'app/shared/model/field-of-study.model';

export const ACTION_TYPES = {
  FETCH_FIELDOFSTUDY_LIST: 'fieldOfStudy/FETCH_FIELDOFSTUDY_LIST',
  FETCH_FIELDOFSTUDY: 'fieldOfStudy/FETCH_FIELDOFSTUDY',
  CREATE_FIELDOFSTUDY: 'fieldOfStudy/CREATE_FIELDOFSTUDY',
  UPDATE_FIELDOFSTUDY: 'fieldOfStudy/UPDATE_FIELDOFSTUDY',
  DELETE_FIELDOFSTUDY: 'fieldOfStudy/DELETE_FIELDOFSTUDY',
  RESET: 'fieldOfStudy/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFieldOfStudy>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type FieldOfStudyState = Readonly<typeof initialState>;

// Reducer

export default (state: FieldOfStudyState = initialState, action): FieldOfStudyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FIELDOFSTUDY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FIELDOFSTUDY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FIELDOFSTUDY):
    case REQUEST(ACTION_TYPES.UPDATE_FIELDOFSTUDY):
    case REQUEST(ACTION_TYPES.DELETE_FIELDOFSTUDY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_FIELDOFSTUDY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FIELDOFSTUDY):
    case FAILURE(ACTION_TYPES.CREATE_FIELDOFSTUDY):
    case FAILURE(ACTION_TYPES.UPDATE_FIELDOFSTUDY):
    case FAILURE(ACTION_TYPES.DELETE_FIELDOFSTUDY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_FIELDOFSTUDY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_FIELDOFSTUDY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FIELDOFSTUDY):
    case SUCCESS(ACTION_TYPES.UPDATE_FIELDOFSTUDY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FIELDOFSTUDY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/field-of-studies';

// Actions

export const getEntities: ICrudGetAllAction<IFieldOfStudy> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FIELDOFSTUDY_LIST,
  payload: axios.get<IFieldOfStudy>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IFieldOfStudy> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FIELDOFSTUDY,
    payload: axios.get<IFieldOfStudy>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IFieldOfStudy> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FIELDOFSTUDY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFieldOfStudy> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FIELDOFSTUDY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFieldOfStudy> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FIELDOFSTUDY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
