from pathlib import Path
from config.config import get_config
from recognition.data.extract_data import load_bin, load_mx_rec

if __name__ == '__main__':
    conf = get_config()
    rec_dir = conf.recog_data_path/'faces_emore'
    load_mx_rec(rec_dir)
    
    # extract dataset
    bin_files = ['agedb_30', 'cfp_fp', 'lfw']
    
    for i in range(len(bin_files)):
        load_bin(rec_dir/(bin_files[i]+'.bin'), rec_dir/bin_files[i], conf.test_transform)