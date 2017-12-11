
package com.trueffect.validation;
import com.trueffect.messages.Message;
import com.trueffect.util.ModelObject;
import com.trueffect.response.ErrorResponse;
import com.trueffect.model.Person;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.DataResourse;
import com.trueffect.util.DataCondition;
import java.sql.Connection;
/**
 * @author santiago.mamani
 */
public class RenterUser implements DataCondition {

    @Override
    public boolean complyCondition(int id, ModelObject resource,   Connection connection) throws Exception {
         Person renterUser = (Person) resource;
         String typeIdentifier = renterUser.getTypeIdentifier();
         if(PersonValidation.isValidTypeIdentifier(typeIdentifier)){
           if(PersonValidation.isValidIdentifier(typeIdentifier,renterUser.getIdentifier())){
             if(!PersonValidation.alreadyExists(typeIdentifier, renterUser.getIdentifier(),connection)){
              if(PersonValidation.isValidLastName(renterUser.getLastName(),DataResourse.MAXIMUM_VALUES)){
                  if(PersonValidation.isValidFirstName(renterUser.getFirstName(),DataResourse.MAXIMUM_VALUES)){
                      if(PersonValidation.isValidGenre(renterUser.getGenre())){
                          if(PersonValidation.isValidBirthday(renterUser.getBirthday())){
                              return true;
                          }else{
                            throw new ErrorResponse(CodeStatus.BAD_REQUEST, Message.NOT_VALID_BIRTHDAY);
                          }
                      }else{
                        throw new ErrorResponse(CodeStatus.BAD_REQUEST, Message.NOT_VALID_GENRE);
                      }
                  }else{
                      throw new ErrorResponse(CodeStatus.BAD_REQUEST, Message.NOT_VALID_FIRST_NAME);
                       }
                  
               }else{
                  throw new ErrorResponse(CodeStatus.BAD_REQUEST, Message.NOT_VALID_LAST_NAME);
                    }
                 }else{
                  throw new ErrorResponse(CodeStatus.BAD_REQUEST, Message.DUPLICATE_IDENTIFIER);
                      }       
           }else{
                throw new ErrorResponse(CodeStatus.BAD_REQUEST, Message.NOT_VALID_IDENTIFIER);
                }
         }else{
            throw new ErrorResponse(CodeStatus.BAD_REQUEST, Message.NOT_VALID_TYPE_IDENTIFIER);
              }
         
    }
     /*
    @Override
    public boolean complyCondition(int id ,ModelObject resource) throws Exception {
        Person renterUser = (Person) resource;
        
        boolean res = false;
        if (PersonValidation.isValidLastName(renterUser.getLast_name(),DataResourse.MAX_CHAR_LNAME_RUSER)) {
            
            if (PersonValidation.isValidFirstName(renterUser.getFirst_name(),DataResourse.MAX_CHAR_FNAME_RUSER)) {
                
                if(PersonValidation.isValidGenre(renterUser.getGenre())){
                    if(PersonValidation.isValidBirthday(renterUser.getBirthday())){
                    res = true;
                    }else{
                    throw new ErrorResponse(CodeStatus.UN_PROCESSABLE_ENTITY, Message.NOT_VALID_BIRTHDAY);
                }
                }else
                {
                    throw new ErrorResponse(CodeStatus.UN_PROCESSABLE_ENTITY, Message.NOT_VALID_GENRE);
                }
                
            } else {
                throw new ErrorResponse(CodeStatus.UN_PROCESSABLE_ENTITY, Message.NOT_VALID_FIRST_NAME);
            }
        } else {

            throw new ErrorResponse(CodeStatus.UN_PROCESSABLE_ENTITY,Message.NOT_VALID_LAST_NAME);

        }
        return res;
    } 
*/
   

}
