//package com.jakespringer.lostexhaust;
//
//import java.io.IOException;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.eclipse.jetty.server.Authentication.User;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import com.jakespringer.lostexhaust.Contact.Entry;
//
//public class UpdateUserBehavior extends RequestBehavior {
//
//    public UpdateUserBehavior() {
//        super("/update-user");
//    }
//
//    @Override
//    public void behavior(HttpServletRequest req, HttpServletResponse res) throws IOException {
//        User currentUser = UserServiceFactory.getUserService().getCurrentUser();
//        if (currentUser != null) {                            
//            @SuppressWarnings("unchecked")
//            Map<String, String[]> map = req.getParameterMap();
//            
//            // possible errors
//            boolean userValidated = false;
//            boolean userSignedUp = false;
//            boolean invalidName = false;
//            boolean invalidEmail = false;
//            boolean invalidNumber = false;
//            boolean invalidNotes = false;
//            boolean invalidPlace = false;
//            
//            // validate user
//            String[] userId = map.get("user_id");
//            Account acc = AccountService.getInstance().getFromGoogle(currentUser.getUserId());
//            if (acc != null) {
//                userSignedUp = true;
//                if (userId != null && userId.length == 1) {
//                    if (userId[0].equals(acc.getId())) {
//                        userValidated = true;
//                    }
//                }
//                
//                if (userValidated) {
//
//                    // update name; should this be done?
//                    String[] nameResult = map.get("name");
//                    if (nameResult != null) {
//                        if (nameResult.length == 1) {
//                            try {
//                                acc.setName(nameResult[0]);
//                            } catch (InvalidAccountElementException e) {
//                                invalidName = true;
//                            }
//                        } else {
//                            invalidName = true;
//                        }
//                    }
//                    
//                    // update contact
//                    Contact newContact = new Contact();
//                    String[] emailsResult = map.get("emails[]");
//                    String[] numbersResult = map.get("numbers[]");
//                    if (emailsResult != null && numbersResult != null) {
//                        for (String e : emailsResult) {
//                            if (e.equals("none")) continue;
//                            try {
//                                newContact.addEmail(new Entry("home", e));
//                            } catch (InvalidEmailException ex) {
//                                invalidEmail = true;
//                            }
//                        }
//                        
//                        for (String n : numbersResult) {
//                            if (n.equals("none")) continue;
//                            try {
//                                newContact.addNumber(new Entry("cell", n));
//                            } catch (InvalidNumberException ex) {
//                                invalidNumber = true;
//                            }
//                        }
//                        
//                        if (!invalidEmail && !invalidNumber) {
//                            try {
//                                acc.setContact(newContact);
//                            } catch (InvalidAccountElementException e1) {
//                                throw new RuntimeException(e1); // better not get this
//                            }
//                        }
//                    } else if (emailsResult == null && numbersResult != null) {
//                        invalidEmail = true;
//                    } else if (emailsResult != null && numbersResult == null) {
//                        invalidNumber = true;
//                    }
//                    
//                    // update notes
//                    String[] notesResult = map.get("notes");
//                    if (notesResult != null) {
//                        if (notesResult.length == 1) {
//                            try {
//                                acc.setNotes(notesResult[0]);
//                            } catch (InvalidAccountElementException e) {
//                                invalidNotes = true;
//                            }
//                        } else {
//                            invalidNotes = true;
//                        }
//                    }
//                    
//                    // update placeId (and along with it address and coordinates)
//                    String[] placeIdResult = map.get("place_id");
//                    if (placeIdResult != null) {
//                        if (placeIdResult.length == 1) {
//                            try {
//                                acc.setPlaceId(placeIdResult[0]);
//                            } catch (InvalidAccountElementException | InvalidPlaceException e) {
//                                invalidPlace = true;
//                            }
//                        } else {
//                            invalidPlace = true;
//                        }
//                    }
//                }
//            }
//            
//            JSONObject obj = new JSONObject();
//            obj.put("status", (userValidated && userSignedUp && !invalidName && 
//                    !invalidEmail && !invalidNumber && !invalidNotes && !invalidPlace) 
//                    ? "OK" : "ERR");
//            obj.put("user_invalid", !userValidated);
//            obj.put("user_not_signed_up", !userSignedUp);
//            obj.put("invalid_name", invalidName);
//            obj.put("invalid_email", invalidEmail);
//            obj.put("invalid_number", invalidNumber);
//            obj.put("invalid_notes", invalidNotes);
//            obj.put("invalid_place", invalidPlace);
//                        
//            try {
//                res.setContentType("application/json");
//                res.setHeader("Cache-Control", "no-cache");
//                obj.write(res.getWriter());
//            } catch (JSONException e) {
//            }
//        } else {
//            JSONObject obj = new JSONObject();
//            obj.put("status", "ERR_NOT_SIGNED_IN");
//                        
//            try {
//                res.setContentType("application/json");
//                res.setHeader("Cache-Control", "no-cache");
//                obj.write(res.getWriter());
//            } catch (JSONException e) {
//            }
//        }
//    }
//}
