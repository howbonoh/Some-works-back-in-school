//
//  ViewController.swift
//  camera_test
//
//  Created by Jac on 2016/10/6.
//  Copyright © 2016年 Jac. All rights reserved.
//

import UIKit

class ViewController: UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate {

    @IBOutlet weak var pickedImaged: UIImageView!
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    
    @IBAction func cameraOpenButton(_ sender: UIButton) {
        if UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.camera) {
            let imagePicker = UIImagePickerController()
            imagePicker.delegate = self
            imagePicker.sourceType = UIImagePickerControllerSourceType.camera;
            imagePicker.allowsEditing = false
            self.present(imagePicker, animated: true, completion: nil)
        }
        
    }
    
    @IBAction func libraryOpenAction(_ sender: UIButton) {
        if UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.photoLibrary){
            let imagePicker = UIImagePickerController()
            imagePicker.delegate = self
            imagePicker.sourceType = UIImagePickerControllerSourceType.photoLibrary;
            imagePicker.allowsEditing = true
            self.present(imagePicker, animated: true, completion: nil)

        }
    }
    
    @IBAction func saveAction(_ sender: UIButton) {
        let imageData = UIImageJPEGRepresentation(pickedImaged.image!, 0.6)
        let compressedJPEGImage = UIImage(data:imageData!)
        UIImageWriteToSavedPhotosAlbum(compressedJPEGImage!, nil, nil, nil)
    }

//    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingImage image:UIImage!, editingInfo : [NSObject : AnyObject]!) {
//        pickedImaged.image = image
//        self.dismiss(animated: true, completion: nil);
//    }
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : AnyObject]) {
        if let image = info[UIImagePickerControllerOriginalImage] as? UIImage {
            pickedImaged.image = image
        } else{
            print("Something went wrong")
        }
        
        self.dismiss(animated: true, completion: nil)
    }
}

