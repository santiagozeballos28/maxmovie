
package com.trueffect.response;
import javax.ws.rs.core.Response.Status.Family;
/**
 * @author Santiago
 */
public class FamilyType {
   public static Family getFamily(int codeStatus){
       Family family = Family.SERVER_ERROR; 
    if ( codeStatus <200 ) { 
      family = Family.INFORMATIONAL; 
    } else if ( codeStatus < 300 ) { 
      family = Family.SUCCESSFUL; 
    } else if ( codeStatus < 400 ) { 
      family = Family.REDIRECTION; 
    } else if ( codeStatus < 500 ) { 
      family = Family.CLIENT_ERROR; 
    }
      return family;
   }
}
